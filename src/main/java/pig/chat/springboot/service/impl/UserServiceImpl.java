package pig.chat.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pig.chat.springboot.common.Result;
import pig.chat.springboot.domain.LoginUser;
import pig.chat.springboot.domain.User;
import pig.chat.springboot.mapper.UserMapper;
import pig.chat.springboot.service.UserService;
import pig.chat.springboot.utils.JwtUtil;

import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public User getById(String id) {
        return userMapper.getById(id);
    }

    @Override
    public Result<Object> login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if (Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = loginUser.getUser().getId();
        String token = JwtUtil.getToken(id);
        userMapper.update(new LambdaUpdateWrapper<User>()
                .set(User::getStatus,1)
                .eq(User::getId,id));


        return Result.success(token,"登录成功");
    }


}
