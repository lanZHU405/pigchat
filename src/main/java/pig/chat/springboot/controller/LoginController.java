package pig.chat.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pig.chat.springboot.common.Codes;
import pig.chat.springboot.common.Result;
import pig.chat.springboot.domain.User;
import pig.chat.springboot.exception.ServiceException;
import pig.chat.springboot.service.UserService;

import java.util.Objects;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/user")
public class LoginController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public Result<Object> login(@RequestBody User user){
        return userService.login(user);
    }
}
