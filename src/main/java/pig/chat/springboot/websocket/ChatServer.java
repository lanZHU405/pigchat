package pig.chat.springboot.websocket;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(value = "/ws")
@Component
public class ChatServer {

    private static final Logger LOGGER = Logger.getLogger(ChatServer.class.getName());

    // 存储频道和订阅该频道的会话
    private static final ConcurrentHashMap<String, Set<Session>> channels = new ConcurrentHashMap<>();

    /**
     * 当客户端连接时触发
     *
     * @param session 当前会话
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println("New connection opened: " + session.getId());
        String jsonResponse = "{\"message\": \"Connection established with ID: " + session.getId() + "\"}";
        session.getBasicRemote().sendText(jsonResponse);
    }

    /**
     * 当客户端发送消息时触发
     *
     * @param message 收到的消息
     * @param session 当前会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message from " + session.getId() + ": " + message);

        try {
            // 解析消息
            Map<String, String> parsedMessage = parseMessage(message);
            String senderId = parsedMessage.get("senderId");
            String receiverId = parsedMessage.get("receiverId");
            String content = parsedMessage.get("message");

            // 创建或加入频道
            String channelId = createChannelId(senderId, receiverId);
            joinChannel(channelId, session);

            // 发布消息到频道
            publishMessage(channelId, content, session);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                session.getBasicRemote().sendText("Error: Invalid message format");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 当客户端断开连接时触发
     *
     * @param session 当前会话
     * @param closeReason 关闭原因
     */
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Session " + session.getId() + " closed because " + closeReason.getReasonPhrase());

        // 从所有频道中移除该会话
        for (Map.Entry<String, Set<Session>> entry : channels.entrySet()) {
            Set<Session> channelSessions = entry.getValue();
            channelSessions.remove(session);
        }
    }

    /**
     * 当发生错误时触发
     *
     * @param session 当前会话
     * @param throwable 错误信息
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        LOGGER.log(Level.SEVERE, "Error in WebSocket session " + session.getId(), throwable);
    }

    /**
     * 创建频道 ID
     *
     * @param senderId 发送者 ID
     * @param receiverId 接收者 ID
     * @return 频道 ID
     */
    private String createChannelId(String senderId, String receiverId) {
        // 确保频道 ID 是唯一的，例如按字典序排列
        if (senderId.compareTo(receiverId) < 0) {
            return senderId + "-" + receiverId;
        } else {
            return receiverId + "-" + senderId;
        }
    }

    /**
     * 加入频道
     *
     * @param channelId 频道 ID
     * @param session 当前会话
     */
    private void joinChannel(String channelId, Session session) {
        Set<Session> channelSessions = channels.computeIfAbsent(channelId, k -> new CopyOnWriteArraySet<>());
        channelSessions.add(session);
    }

    /**
     * 发布消息到频道
     *
     * @param channelId 频道 ID
     * @param message 消息内容
     * @param senderSession 发送者会话
     */
    private void publishMessage(String channelId, String message, Session senderSession) throws IOException {
        Set<Session> channelSessions = channels.get(channelId);
        if (channelSessions != null) {
            for (Session session : channelSessions) {
                if (session.isOpen() && !session.equals(senderSession)) {
                    session.getBasicRemote().sendText(message);
                }
            }
        }
    }

    /**
     * 解析消息
     *
     * @param message 消息内容
     * @return 解析后的消息
     */
    private Map<String, String> parseMessage(String message) {
        Map<String, String> result = new HashMap<>();
        // 假设消息格式为 JSON，例如 {"senderId": "123", "receiverId": "456", "message": "Hello"}
        // 这里可以使用 JSON 解析库，例如 Jackson 或 Gson
        // 这里简化处理，实际项目中建议使用 JSON 库
        int senderIdStart = message.indexOf("\"senderId\": \"") + "\"senderId\": \"".length();
        int senderIdEnd = message.indexOf("\"", senderIdStart);
        result.put("senderId", message.substring(senderIdStart, senderIdEnd));

        int receiverIdStart = message.indexOf("\"receiverId\": \"") + "\"receiverId\": \"".length();
        int receiverIdEnd = message.indexOf("\"", receiverIdStart);
        result.put("receiverId", message.substring(receiverIdStart, receiverIdEnd));

        int messageStart = message.indexOf("\"message\": \"") + "\"message\": \"".length();
        int messageEnd = message.indexOf("\"}", messageStart);
        result.put("message", message.substring(messageStart, messageEnd));

        return result;
    }
}
