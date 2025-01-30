package pig.chat.springboot.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pig.chat.springboot.domain.Talk;
import pig.chat.springboot.domain.TalkDocument;
import pig.chat.springboot.service.TalkDocumentService;

import java.util.ArrayList;
import java.util.List;

@Component
public class RabbitMessageListener {

    @Autowired
    private TalkDocumentService talkDocumentService;

    @RabbitListener(queues = "queue.pigchat")
    public void receiveFanoutMessage(List<Talk> talks) {
        List<TalkDocument> list = new ArrayList<>();
        for (Talk talk : talks) {
            TalkDocument talkDocument = new TalkDocument();
            BeanUtils.copyProperties(talk,talkDocument);
            list.add(talkDocument);
        }
        talkDocumentService.saveAll(list);
    }
}
