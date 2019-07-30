package com.tavisca.chat;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/endpoint")
public class ChatWebSocket {
    private ChatHelper chatHelper;

    public ChatWebSocket() {
        chatHelper = new ChatHelper();
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Open Connenction: " + session.getId());
        ChatHelper.sessionHashMap.put(session.getId(), session);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Close Connection: " + session.getId());
        ChatHelper.sessionHashMap.remove(session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received message: from" + session.getId() + "Message is " + message);
        chatHelper.sendMessage(message, session.getId());
        try {
            session.getBasicRemote().sendText(message.toUpperCase());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
