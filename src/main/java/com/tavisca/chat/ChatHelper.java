package com.tavisca.chat;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatHelper {
    public static HashMap<String, Session> sessionHashMap = new HashMap<>();

    public void sendMessage(String message, String senderId) {
        Pattern pattern = Pattern.compile("@(.*):(.*)");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            String receiverId = matcher.group(1);
            String messageToSend = matcher.group(2);

            Session receiverSession = sessionHashMap.get(receiverId);

            try {
                receiverSession.getBasicRemote().sendText("From " + senderId + " Message is" + messageToSend);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
