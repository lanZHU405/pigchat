package pig.chat.springboot.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;
import pig.chat.springboot.domain.TalkDocument;

import java.util.List;

@Service
public interface TalkDocumentService extends MongoRepository<TalkDocument,String> {

    @Query("{ $or: [ { $and: [ { 'sender_id' : ?0 }, { 'received_id' : ?1 } ] }," +
            " { $and: [ { 'sender_id' : ?1 }, { 'received_id' : ?0 } ] } ] }")
    List<TalkDocument> findMessage(String senderId, String receiveId);
}
