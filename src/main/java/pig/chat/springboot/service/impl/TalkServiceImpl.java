package pig.chat.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pig.chat.springboot.domain.Talk;
import pig.chat.springboot.mapper.TalkMapper;

@Service
public class TalkServiceImpl extends ServiceImpl<TalkMapper, Talk> {
}
