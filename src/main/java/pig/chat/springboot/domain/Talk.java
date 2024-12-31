package pig.chat.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("talk")
public class Talk {

    private String id;

    // 发送者id
    @TableField("sender_id")
    private String senderId;

    // 接收者id
    @TableField("received_id")
    private String receiveId;

    // 消息内容
    @TableField("message")
    private String message;

    // 发送时间
    @TableField("send_at")
    private Date sendAt;

    // 是否已读
    @TableField("is_read")
    private boolean isRead;

    // 消息类型
    @TableField("message_type")
    private String messageType;

    // 是否是回复的消息
    @TableField("reply_id")
    private String replyId;
}
