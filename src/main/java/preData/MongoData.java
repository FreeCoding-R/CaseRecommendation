package preData;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

/**
 * Created by loick on 17/07/2017.
 */
public class MongoData {
    private static MongoDatabase mongoDatabase;

    public static MongoDatabase getDataBase() {
        if(mongoDatabase == null) {
            String sURI = String.format("mongodb://%s:%s@%s:%d/%s", "freecoding", "freecoding", "139.224.233.63", 27017, "freecoding");
            MongoClientURI uri = new MongoClientURI(sURI);
            MongoClient mongoClient = new MongoClient(uri);

            mongoDatabase = mongoClient.getDatabase("freecoding");
        }
        return mongoDatabase;
    }
}
