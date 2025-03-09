package pig.chat.springboot.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pig.chat.springboot.domain.LoginUser;
import pig.chat.springboot.domain.User;
import pig.chat.springboot.mapper.MenuMapper;
import pig.chat.springboot.mapper.UserMapper;
import pig.chat.springboot.service.UserService;
import pig.chat.springboot.utils.JwtUtil;
import pig.chat.springboot.utils.RedisUtil;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private UserMapper userMapper;
    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private MenuMapper menuMapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        log.info(request.toString());
        if (token == null || token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        String id;
        try {
            Claims claims = jwtUtil.parseToken(token);
            id = claims.getId(); // 假设JWT中的用户ID存储在subject字段中
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        User user = (User)redisUtil.get("user:online" + id);

        if (user == null){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not login or no exists");
            return;
        }
        redisUtil.set("user:online"+id,user,8*60*60);

        LoginUser loginUser = new LoginUser(user,menuMapper.getPermission(user));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
