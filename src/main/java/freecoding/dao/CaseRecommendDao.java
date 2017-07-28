package freecoding.dao;

import org.bson.Document;

import java.io.File;
import java.util.List;

/**
 * Created by zhujing on 2017/7/17.
 */
public interface CaseRecommendDao {

    Document find(String id);

    Document findByUser(String id);


    List<Document> getRandomCases();

    List<Document> getKeyCases(Document document);

    List<Document> getKmeansCases(int location);

    List<Document> getKmeansCases(File file);
}