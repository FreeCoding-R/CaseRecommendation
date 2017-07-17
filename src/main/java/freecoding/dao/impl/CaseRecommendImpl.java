package freecoding.dao.impl;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import freecoding.dao.CaseRecommendDao;
import freecoding.util.MongoData;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loick on 17/07/2017.
 */
@Repository
public class CaseRecommendImpl implements CaseRecommendDao {

    @Override
    public List<Document> getRandomCases() {
        MongoCollection<Document> collection = MongoData.getDataBase().getCollection("traffic");
        List<Document> randomList = new ArrayList<>();
        collection.find().limit(6).forEach((Block<? super Document>) item->{
            randomList.add(item);
        });
        return randomList;
    }
}
