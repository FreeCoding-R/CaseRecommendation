package freecoding.dao;

import freecoding.entity.JDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by loick on 15/07/2017.
 */
public interface JDocumentRepository extends MongoRepository<JDocument, Long>{
    JDocument findJDocumentByCharacter(String character);

    List<JDocument> findByCharacterLike(String character);

    @Query(value = "{'info':?0}", fields = "{'character':1}")
    List<JDocument> findByInfoLike(String info);
}
