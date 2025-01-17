package pig.chat.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pig.chat.springboot.domain.Talk;

@RestController
@RequestMapping("/talk")
@Slf4j
public class TalkController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody Talk talk){
        rabbitTemplate.convertAndSend("exchange.pigchat", "", talk);
    }


}
