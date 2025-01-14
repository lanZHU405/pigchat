package pig.chat.springboot.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import pig.chat.springboot.domain.TalkDocument;

@Service
public interface TalkDocumentService extends MongoRepository<TalkDocument,String> {
}
