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
import preData.CasesFromPython;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by loick on 17/07/2017.
 */
@Repository
public class CaseRecommendDaoImpl implements CaseRecommendDao {

    private static String collectionName = "tianjin";
    private static MongoDatabase database = MongoData.getDataBase();

    @Override
    public Document find(String id) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        FindIterable<Document> cursor = collection.find(query);
        Document document=cursor.iterator().next();

        return document;
    }

    @Override
    public Document findByName(String name) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        BasicDBObject query = new BasicDBObject();
        query.put("@value" , "天津市河东区人民法院 刑事判决书 （2011）东刑初字第307号");
        FindIterable<Document> cursor = collection.find(query);
        Document document=cursor.iterator().next();

        return document;
    }

    @Override
    public List<Document> getRandomCases() {
        MongoCollection<Document> collection = database.getCollection(collectionName);
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

    @Override
    public List<Document> getKmeansCases(File xmlfile) {

        List<Integer> indexs = CasesFromPython.getIndex(xmlfile);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        List<Document> documents = new ArrayList<>();
        for(Integer integer:indexs){
            Document document = collection.find(eq("documentID", integer)).first();
            documents.add(document);
        }
        return documents;
    }

    private List<Document> getCases(Document document){

        MongoCollection<Document> collection = database.getCollection(collectionName);

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
