package freecoding.dao.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import freecoding.dao.CaseRecommendDao;
import freecoding.util.MongoData;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loick on 17/07/2017.
 */
@Repository
public class CaseRecommendDaoImpl implements CaseRecommendDao {

    @Override
    public Document find(String id) {
        MongoCollection<Document> collection = MongoData.getDataBase().getCollection("traffic");
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        FindIterable<Document> cursor = collection.find(query);
        Document document=cursor.iterator().next();

        return document;
    }

    @Override
    public List<Document> getRandomCases() {
        MongoCollection<Document> collection = MongoData.getDataBase().getCollection("traffic");
        List<Document> randomList = new ArrayList<>();
        collection.find().limit(6).forEach((Block<? super Document>) item->{
            randomList.add(item);
        });

        return randomList;
    }

    @Override
    public List<Document> getKeyCases(Document document) {

        List<Document> cases = getCases(document);
        Document subDocument1 = getSubDocument(document);

        for (String key : subDocument1.keySet()) {
            System.out.println(key);
            Document inDocument1 = (Document) subDocument1.get(key);
            for (String inkey : inDocument1.keySet()) {
                if(inDocument1.get(inkey) instanceof String)
                    continue;
                Document indocument2 = (Document)inDocument1.get(inkey);
                for(int i = 0; i<cases.size(); i++){
                    Document caseDocu = cases.get(i);
                    Document inCaseDocu2 = (Document)((Document)caseDocu.get(key)).get(inkey);

                    if(!inCaseDocu2.equals(indocument2)) {
                        cases.remove(i);
                        i--;
                        if(cases.size() < 7){
                            return cases;
                        }
                    }
                }
            }
        }
        return cases;
    }

    private List<Document> getCases(Document document){

        MongoDatabase database = MongoData.getDataBase();


        MongoCollection<Document> collection = database.getCollection("traffic");

        List<Document> cases = new ArrayList<>();

        BasicDBObject exclude = new BasicDBObject();
        exclude.append("_id",1);
        exclude.append("SSJL",1);
        exclude.append("CPFXGC",1);
        exclude.append("PJJG",1);
        collection.find().limit(100).projection(exclude).forEach((Block<? super Document>) item->{
            cases.add(item);
        });

        return cases;
    }

    private Document getSubDocument(Document document){
        Document subDocument = new Document();
        String[] keySet = {"SSJL", "CPFXGC", "PJJG"};
        for (String key: keySet){
            subDocument.append(key, document.get(key));
        }
        return subDocument;
    }


}
