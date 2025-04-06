package pig.chat.springboot.utils;


import org.springframework.security.core.context.SecurityContextHolder;
import pig.chat.springboot.domain.User;

public class SecurityUtil {

    public static User getUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
