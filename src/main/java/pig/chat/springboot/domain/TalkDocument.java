package pig.chat.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document(collection = "talk")
@Data
public class TalkDocument {

    @MongoId
    private String id;

    // 发送者id
    @Field("sender_id")
    private String senderId;

    // 接收者id
    @Field("received_id")
    private String receiveId;

    // 消息内容
    @Field("message")
    private String message;

    // 发送时间
    @Field("send_at")
    private Date sendAt;

    // 是否已读
    @Field("is_read")
    private boolean isRead;

    // 消息类型
    @Field("message_type")
    private String messageType;

    // 是否是回复的消息
    @Field("reply_id")
    private String replyId;
}
