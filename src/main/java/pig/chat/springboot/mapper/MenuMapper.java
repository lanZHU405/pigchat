package pig.chat.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pig.chat.springboot.domain.Menu;
import pig.chat.springboot.domain.User;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    public List<String> getPermission(User user);
}
