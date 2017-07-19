package preData;


import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import freecoding.util.MongoData;
import net.sf.json.xml.XMLSerializer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;

//import com.mongodb.util.JSON;

/**
 * Created by loick on 16/07/2017.
 */
public class Xml2mongodb {
    public static void main(String[] args) throws DocumentException {
        File direc = new File("/Users/loick/Desktop/卓越工程师/天津/危险驾驶罪");
        File files[] = direc.listFiles();

        if(files.length != 0){

//            SAXReader sr = new SAXReader();
//            Document document = (Document) sr.read(files[0]);
//
//            String responseTextObj = document.asXML();
//
//            responseTextObj = responseTextObj.replace("\r\n", "\\r\\n");
//
//            XMLSerializer xmlSerializer = new XMLSerializer();
//            net.sf.json.JSON jsonObj = xmlSerializer.read(responseTextObj);
//
//            String jsonStr = jsonObj.toString();
//
//
//
//
//            DBObject object = (DBObject) JSON.parse(jsonStr);
//
//            System.out.println(object);



            MongoDatabase mongoDatabase = MongoData.getDataBase();

            MongoCollection<DBObject> collection = mongoDatabase.getCollection("dangerDrive",DBObject.class);


            for(File file: files) {
                SAXReader sr = new SAXReader();
                Document document = (Document) sr.read(file);
                String responseTextObj = document.asXML();
                responseTextObj = responseTextObj.replace("\r\n", "\\r\\n");
                XMLSerializer xmlSerializer = new XMLSerializer();
                net.sf.json.JSON jsonObj = xmlSerializer.read(responseTextObj);
                String jsonStr = jsonObj.toString();

                DBObject object = (DBObject) JSON.parse(jsonStr);

                collection.insertOne(object);
            }

        }
    }
}
