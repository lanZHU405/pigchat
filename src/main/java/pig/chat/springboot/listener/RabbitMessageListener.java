package pig.chat.springboot.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pig.chat.springboot.service.TalkDocumentService;

@Component
public class RabbitMessageListener {

    @Autowired
    private TalkDocumentService talkDocumentService;

    @RabbitListener(queues = "queue.pigchat")
    public void receiveFanoutMessage(String message) {
        System.out.println("Received Fanout Message: " + message);
    }
}
