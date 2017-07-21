package freecoding.dao;

import org.bson.Document;

import java.util.List;

/**
 * Created by zhujing on 2017/7/17.
 */
public interface CaseRecommendDao {

    Document find(String id);

    Document findByName(String name);

    List<Document> getRandomCases();

    List<Document> getKeyCases(Document document);
}
