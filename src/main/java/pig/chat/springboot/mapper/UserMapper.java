package pig.chat.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pig.chat.springboot.domain.User;

import java.util.List;


public interface UserMapper extends BaseMapper<User> {

    @Select("select * from users where id = #{id}")
    User getById(String id);

    List<User> getFriendById(String id);
}
