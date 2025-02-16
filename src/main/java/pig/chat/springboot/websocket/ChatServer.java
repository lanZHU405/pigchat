package pig.chat.springboot.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/chat")
public class ChatServer {

    // 存储所有连接的客户端
    private static final Set<Session> sessions = Collections.synchronizedSet(new CopyOnWriteArraySet<>());

    // 存储频道和订阅者
    private static final Map<String, Set<Session>> channels = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("Client connected: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received message: " + message);

        try {
            // 解析消息
            String[] parts = message.split(",");
            String type = parts[0];
            String channel = parts[1];

            if ("subscribe".equals(type)) {
                // 订阅频道
                channels.computeIfAbsent(channel, k -> new CopyOnWriteArraySet<>()).add(session);
                System.out.println("Client subscribed to channel: " + channel);
            } else if ("publish".equals(type)) {
                // 发布消息
                String content = parts[2];
                Set<Session> subscribers = channels.get(channel);
                if (subscribers != null) {
                    for (Session subscriber : subscribers) {
                        if (subscriber.isOpen()) {
                            subscriber.getBasicRemote().sendText(content);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        // 移除订阅
        for (Set<Session> subscribers : channels.values()) {
            subscribers.remove(session);
        }
        System.out.println("Client disconnected: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Error occurred for session: " + session.getId());
        throwable.printStackTrace();
    }
}
