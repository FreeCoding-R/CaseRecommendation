package freecoding.service.impl;

import freecoding.dao.DocumentRepository;
import freecoding.entity.Document;
import org.junit.Assert;
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
public class DocumentTest {
    @Autowired
    private DocumentRepository documentRepository;

    @Before
    public void SetUp(){
        documentRepository.deleteAll();
    }

    @Test
    public void test(){
        documentRepository.save(new Document("案例文书","连环杀人案"));
        documentRepository.save(new Document("案例文书","连环杀人案2"));

        Assert.assertEquals(2, documentRepository.findAll().size());
    }
}
