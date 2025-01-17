package pig.chat.springboot.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pig.chat.springboot.domain.Talk;
import pig.chat.springboot.domain.TalkDocument;
import pig.chat.springboot.service.TalkDocumentService;

@Component
public class RabbitMessageListener {

    @Autowired
    private TalkDocumentService talkDocumentService;

    @RabbitListener(queues = "queue.pigchat")
    public void receiveFanoutMessage(TalkDocument talk) {
        talkDocumentService.save(talk);
    }
}
