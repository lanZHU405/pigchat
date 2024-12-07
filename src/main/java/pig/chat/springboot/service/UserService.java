package pig.chat.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pig.chat.springboot.common.Result;
import pig.chat.springboot.domain.User;

public interface UserService extends IService<User> {
    User getById(String id);

    Result<Object> login(User user);
}
