package freecoding.service.impl;

import freecoding.dao.JDocumentRepository;
import freecoding.entity.JDocument;
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
public class JDocumentTest {
    @Autowired
    private JDocumentRepository documentRepository;

    @Before
    public void SetUp(){
        documentRepository.deleteAll();
    }

    @Test
    public void test(){
        documentRepository.save(new JDocument("案例文书","连环杀人案"));
        documentRepository.save(new JDocument("案例文书","连环杀人案2"));

        Assert.assertEquals(2, documentRepository.findAll().size());

 //       Assert.assertEquals(documentRepository.findDocumentByCharacter("连环杀人案2").getInfo(),"案例文书");
    }

}
