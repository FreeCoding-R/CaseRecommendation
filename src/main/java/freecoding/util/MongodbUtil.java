package freecoding.util;

import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import freecoding.dao.RecordRepository;
import freecoding.entity.Record;
import net.sf.json.xml.XMLSerializer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by loick on 17/07/2017.
 */
@Service
public class MongodbUtil {
    @Autowired
    private RecordRepository recordRepository;

    private static MongoDatabase mongoDatabase;
    MongoClient mongoClient;

    MongoDatabase database;

    @Autowired
    public MongodbUtil(MongoClient mongoClient){
        this.mongoClient = mongoClient;
        database = mongoClient.getDatabase("freecoding");
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    //    public static MongoDatabase getDataBase() {
////        if(mongoDatabase == null) {
////            System.out.println("connected");
////            String sURI = String.format("mongodb://%s:%s@%s:%d/%s", "freecoding", "freecoding", "139.224.233.63", 27017, "freecoding");
////            MongoClientURI uri = new MongoClientURI(sURI);
////            MongoClient mongoClient = new MongoClient(uri);
////
////            mongoDatabase = mongoClient.getDatabase("freecoding");
////        }
//        return mongoDatabase;
//
////        return new MongodbUtil().mongoClient.getDatabase("freecoding");
//    }





    public void insert(File file,String userName) throws DocumentException {

        MongoDatabase mongoDatabase = database;
        MongoCollection<DBObject> collection = mongoDatabase.getCollection("cases",DBObject.class);
        SAXReader sr = new SAXReader();
        Document document = (Document) sr.read(file);

        String name=document.getRootElement().element("QW").element("WS").attribute("value").getText();
        String responseTextObj = document.asXML();
        responseTextObj = responseTextObj.replace("\r\n", "\\r\\n");
        XMLSerializer xmlSerializer = new XMLSerializer();
        net.sf.json.JSON jsonObj = xmlSerializer.read(responseTextObj);
        String jsonStr = jsonObj.toString();
        DBObject object = (DBObject) JSON.parse(jsonStr);


        //by zj
        object.put("name",name);
        collection.insertOne(object);
        String a=object.get("_id").toString();
        String b=object.get("name").toString();

        Record record=new Record();
        record.setUserName(userName);
        record.setFileId(a);
        record.setFileName(b);
        recordRepository.save(record);
    }
}
