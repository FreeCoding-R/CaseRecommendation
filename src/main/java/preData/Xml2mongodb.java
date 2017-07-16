package preData;


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
 * Created by loick on 16/07/2017.
 */
public class Xml2mongodb {
    public static void main(String[] args) throws DocumentException {
        File direc = new File("/Users/loick/Desktop/卓越工程师/天津/危险驾驶罪");
        File files[] = direc.listFiles();

        if(files.length != 0){

           // MongoClient mongoClient = new MongoClient( "139.224.233.63" , 27017 );

            String sURI = String.format("mongodb://%s:%s@%s:%d/%s", "freecoding", "freecoding", "139.224.233.63", 27017, "freecoding");
            MongoClientURI uri = new MongoClientURI(sURI);
            MongoClient mongoClient = new MongoClient(uri);

            MongoDatabase mongoDatabase = mongoClient.getDatabase("freecoding");

            MongoCollection<DBObject> collection = mongoDatabase.getCollection("dangerDrive",DBObject.class);



            for(File file: files) {
                SAXReader sr = new SAXReader();
                Document document = (Document) sr.read(file);
                String responseTextObj = document.asXML();
                XMLSerializer xmlSerializer = new XMLSerializer();
                net.sf.json.JSON jsonObj = xmlSerializer.read(responseTextObj);
                String jsonStr = jsonObj.toString();

                DBObject object = (DBObject) JSON.parse(jsonStr);
                collection.insertOne(object);
            }

        }
    }
}
