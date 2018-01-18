/**
 * Author: Binay Mishra
 */

//Create stomp client over sockJS protocol
var socket = new SockJS("/stock-market");
var stompClient = Stomp.over(socket);

//Render price data from server into HTML, registered as callback
//when subscribing to price topic
function renderPrice(frame) {
	var prices = JSON.parse(frame.body);
	$('#price').empty();
	for ( var i in prices) {
		var price = prices[i];
		$('#price').append(
				$('<tr>').append($('<td>').html(price.name),
						$('<td>').html(price.price.toFixed(2)),
						$('<td>').html(price.timeStr)));
	}
}

// Callback function to be called when stomp client is connected to server
var connectCallback = function() {
	stompClient.subscribe('/topic/stock', renderPrice);
};

//Callback function to be called when stomp client could not connect to server
var errorCallback = function(error) {
	alert(error.headers.message);
};

// Connect to server via websocket
stompClient.connect("guest", "guest", connectCallback, errorCallback);