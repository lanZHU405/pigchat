package pig.chat.springboot.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pig.chat.springboot.service.MessageSenderService;

@Service
public class MessageSenderServiceImpl implements MessageSenderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendPigchatMessage(String message) {
        rabbitTemplate.convertAndSend("exchange.pigchat","",message);
    }
}
