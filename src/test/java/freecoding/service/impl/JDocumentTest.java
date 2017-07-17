package freecoding.service.impl;

import freecoding.dao.JDocumentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by loick on 15/07/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JDocumentTest {
    @Autowired
    private JDocumentRepository documentRepository;

    @Before
    public void SetUp(){
        documentRepository.deleteAll();
    }

    @Test
    public void test(){
//        MongoDatabase database = MongoData.getDataBase();
//
//        MongoDatabase mongoDatabase = MongoData.getDataBase();
//
//        MongoCollection<DBObject> collection = mongoDatabase.getCollection("dangerDrive",DBObject.class);
//
//        Assert.assertEquals("596b23daec393fc7033044ab", collection.find().first().get("_id"));

 //       Assert.assertEquals(documentRepository.findDocumentByCharacter("连环杀人案2").getInfo(),"案例文书");
    }

}
