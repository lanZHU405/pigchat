package pig.chat.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/ai")
public class AIController {

    @RequestMapping
    public String ReplyMessage(String message){
        return "777";
    }

}
