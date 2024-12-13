package pig.chat.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pig.chat.springboot.domain.LoginUser;
import pig.chat.springboot.domain.User;
import pig.chat.springboot.mapper.MenuMapper;
import pig.chat.springboot.mapper.UserMapper;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        if (Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }
        List<String> list = menuMapper.getPermission(user);
        return new LoginUser(user,list);
    }
}
