package freecoding.util;

import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import net.sf.json.xml.XMLSerializer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;

/**
 * Created by loick on 17/07/2017.
 */
public class MongodbUtil {
    private static MongoDatabase mongoDatabase;

    public static MongoDatabase getDataBase() {
        if(mongoDatabase == null) {
            System.out.println("connected");
            String sURI = String.format("mongodb://%s:%s@%s:%d/%s", "freecoding", "freecoding", "139.224.233.63", 27017, "freecoding");
            MongoClientURI uri = new MongoClientURI(sURI);
            MongoClient mongoClient = new MongoClient(uri);

            mongoDatabase = mongoClient.getDatabase("freecoding");
        }
        return mongoDatabase;
    }



    public static void insert(File file,String userName) throws DocumentException {

        MongoDatabase mongoDatabase = MongodbUtil.getDataBase();
        MongoCollection<DBObject> collection = mongoDatabase.getCollection("casesByUser",DBObject.class);
        SAXReader sr = new SAXReader();
        Document document = (Document) sr.read(file);

        String name=document.getRootElement().element("QW").element("WS").attribute("value").getText();
        String responseTextObj = document.asXML();
        responseTextObj = responseTextObj.replace("\r\n", "\\r\\n");
        XMLSerializer xmlSerializer = new XMLSerializer();
        net.sf.json.JSON jsonObj = xmlSerializer.read(responseTextObj);
        String jsonStr = jsonObj.toString();
        DBObject object = (DBObject) JSON.parse(jsonStr);

//        object.put("documentID", i);

        //by zj
        object.put("name",name);
        object.put("user",userName);
        collection.insertOne(object);

    }
}
