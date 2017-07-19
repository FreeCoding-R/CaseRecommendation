package preData;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import freecoding.dao.CaseRecommendDao;
import freecoding.dao.impl.CaseRecommendDaoImpl;
import freecoding.util.MongoData;
import org.bson.Document;

import java.util.List;

/**
 * Created by loick on 18/07/2017.
 */
public class Count {

    public static void main(String args[]) {
        MongoDatabase database = MongoData.getDataBase();


        MongoCollection<Document> collection = database.getCollection("traffic");

        Document document = collection.find().first();

        CaseRecommendDao caseRecommendDao = new CaseRecommendDaoImpl();

        List<Document> caselist = caseRecommendDao.getKeyCases(document);

        System.out.println(caselist.size());


    }
}

