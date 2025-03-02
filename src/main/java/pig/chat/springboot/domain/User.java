package pig.chat.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("users")
public class User {

    private String id;

    private String username;

    private String password;

    private String email;

    private Date createdAt;

    private Date updatedAt;

    private Date lastLoginAt;

    private Integer status;

    private String sex;

    private String userType;

    private String avatar;

    private String phoneNumber;

    private String signature;

    private String nickName;

    private String backgroundImg;
}
