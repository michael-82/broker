package org.aktin.broker.client2;

import java.io.IOException;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WebsocketClientListener implements WebSocket.Listener{
	private NotificationListener listener;

	@Override
	public void onOpen(WebSocket webSocket) {
		Listener.super.onOpen(webSocket);
	}

	@Override
	public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
		String dataString = ""+data;
		int sep = dataString.indexOf(' ');
		webSocket.request(1);
		if( last == true && sep > 0 ) {
			String command = dataString.substring(0, sep);
			String arg = dataString.substring(sep+1);
			switch( command ) {
			case "published":
				listener.onRequestPublished(Integer.valueOf(arg));
				break;
			case "closed":
				listener.onRequestClosed(Integer.valueOf(arg));
				break;
			default:
				// ignoring unsupported websocket command
				// TODO log warning
			}
			return CompletableFuture.completedStage(null);
		}else {
			// all messages are short and there should be no partial messages
			return CompletableFuture.failedStage(new IOException("Partial websocket message not supported"));
		}
	}

	@Override
	public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
		listener.onWebsocketClosed(statusCode, reason);
		return null; // websocket can be closed immediately
	}

}
