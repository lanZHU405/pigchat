package pig.chat.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pig.chat.springboot.common.Codes;
import pig.chat.springboot.common.Result;
import pig.chat.springboot.domain.Talk;
import pig.chat.springboot.domain.TalkDocument;
import pig.chat.springboot.service.TalkDocumentService;

import java.util.List;

@RestController
@RequestMapping("/talk")
@Slf4j
public class TalkController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TalkDocumentService talkDocumentService;

    @PostMapping("/sendMessage")
    public Result<String> sendMessage(@RequestBody List<Talk> talk){
        rabbitTemplate.convertAndSend("exchange.pigchat", "", talk);
        return Result.success(Codes.SUCCESS);
    }

    @PostMapping("/getMessageData")
    public Result<List<TalkDocument>> getMessageData(@RequestBody TalkDocument talk){
        List<TalkDocument> message = talkDocumentService.findMessage(talk.getSenderId(), talk.getReceiveId());
        return Result.success(message);
    }


}
