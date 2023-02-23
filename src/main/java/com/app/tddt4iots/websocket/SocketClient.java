package com.app.tddt4iots.websocket;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.stereotype.Service;

@Service
public class SocketClient {
    private final SocketIOServer server;
    private  final SocketIONamespace namespace;

    public SocketClient(SocketIOServer server) {
        this.server = server;
        this.namespace = server.getNamespace("");
    }

    public void sendMessage(String mensaje, String sala){
        namespace.getRoomOperations(sala).sendEvent("get_message", new Message(MessageType.SERVER, mensaje));
    }
}
