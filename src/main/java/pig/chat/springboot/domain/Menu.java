package pig.chat.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_menu")
public class Menu implements Serializable {

    private Long id;

    private String menuName;

    private String path;

    private String component;

    private String visible;

    private String status;

    private String perms;

    private String icon;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    private String remark;
}
