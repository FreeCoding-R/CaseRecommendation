package freecoding.dao;

import freecoding.entity.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by loick on 15/07/2017.
 */
public interface DocumentRepository extends MongoRepository<Document, Long>{
    Document findDocumentByCharacter(String character);
}
