package pig.chat.springboot.controller;

import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pig.chat.springboot.common.Codes;
import pig.chat.springboot.common.Result;
import pig.chat.springboot.domain.User;
import pig.chat.springboot.exception.ServiceException;
import pig.chat.springboot.service.UserService;
import pig.chat.springboot.utils.JwtUtil;

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

    @GetMapping("/isLogin")
    @ResponseBody
    public Result<User> isLogin(@RequestParam String token){
        String id;
        try {
            Claims claims = JwtUtil.parseToken(token);
            id = claims.getId(); // 假设JWT中的用户ID存储在subject字段中
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(Codes.NOT_LOGIN);
        }

        User user = userService.getById(id);
        if (user!=null){
            return Result.success(user);
        }
        return Result.error(Codes.NOT_LOGIN);
    }

    @GetMapping("/getFriend/{id}")
    @ResponseBody
    public Result<List<User>> getFriend(@PathVariable String id){
        return userService.getFriendById(id);
    }

    @GetMapping("/getUserInfo/{id}")
    @ResponseBody
    public Result<User> getUserInfo(@PathVariable String id){
        return Result.success(userService.getById(id));
    }

    @PostMapping("/update")
    public Result<String> updateUser(@RequestBody User user){
        return Result.success(userService.updateById(user)+"");
    }
}
