package pig.chat.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pig.chat.springboot.common.Result;
import pig.chat.springboot.domain.User;

import java.util.List;

public interface UserService extends IService<User> {
    User getById(String id);

    Result<Object> login(User user);

    Result<Object> logout();

    Result<List<User>> getFriendById(String id);
}
