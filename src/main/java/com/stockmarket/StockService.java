package com.stockmarket;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;



@Service
public class StockService {
	
	final Random rand = new Random(System.currentTimeMillis());
	final String STOCK_QUEUE = "/topic/stock";
	
	private StockMarketRepository repository;
	
	private ThreadPoolTaskScheduler scheduler;
	
	private SimpMessagingTemplate template; 
	
	@Autowired
	public StockService(StockMarketRepository repository, ThreadPoolTaskScheduler scheduler,
			SimpMessagingTemplate template) {
		super();
		this.repository = repository;
		this.scheduler = scheduler;
		this.template = template;
	}

	
	private void updateStockPrices(List<Stock> stockPrices) {
		
		for (Stock stock : stockPrices) {
			double chgPct = rand.nextDouble() * 5.0;
			if (rand.nextInt(2) == 1)
				chgPct = -chgPct;
			stock.setPrice(stock.getPrice() + (chgPct / 100.0 * stock.getPrice()));
			
		}
		template.convertAndSend(STOCK_QUEUE, stockPrices);
	}
	
	@PostConstruct
	public void broadcastEverySecond() {
		insertSomeData();
		final List<Stock> stockPrices = new ArrayList<>(repository.findAll());
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				updateStockPrices(stockPrices);
			}
		}, 1000);
	}
	

	private void insertSomeData() {
			repository.save(Stream.of(
					new Stock(1L,"IBM", 66.79),
					new Stock(2L, "TCS", 44.18),
					new Stock(3L, "HCL", 78.32)
					).collect(Collectors.toList()));
	}

}
