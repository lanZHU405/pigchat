package pig.chat.springboot.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint("/ws")
@Component
public class ChatServer {

    private static final Logger LOGGER = Logger.getLogger(ChatServer.class.getName());

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("New connection opened: " + session.getId());
        try {
            session.getBasicRemote().sendText("Connection established with ID: " + session.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("Message from " + session.getId() + ": " + message);
        // Echo the received message back to the client
        session.getBasicRemote().sendText("Echo: " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Session " + session.getId() + " closed because " + closeReason.getReasonPhrase());
        // 可以在这里执行清理操作，例如移除session相关的资源
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // 错误处理逻辑
        LOGGER.log(Level.SEVERE, "Error in WebSocket session " + session.getId(), throwable);
        // 根据需要可以尝试恢复连接或者记录日志等
    }
}
