package pig.chat.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pig.chat.springboot.domain.Talk;

@Mapper
public interface TalkMapper extends BaseMapper<Talk> {
}
