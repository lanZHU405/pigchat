package pig.chat.springboot.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pig.chat.springboot.common.Result;
import pig.chat.springboot.domain.User;
import pig.chat.springboot.service.UserService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public Result<Object> login(@RequestBody User user){
        return userService.login(user);
    }

    @PostMapping("/logout")
    @ResponseBody
    public Result<Object> logout(){
        return userService.logout();
    }

    @PreAuthorize("hasAuthority('system:user:list')")
    @GetMapping("/list")
    @ResponseBody
    public Result<List<User>> getList(){
        return Result.success(userService.list());
    }
}
