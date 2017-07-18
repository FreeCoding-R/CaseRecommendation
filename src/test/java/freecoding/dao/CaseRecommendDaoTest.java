package freecoding.dao;

import freecoding.dao.impl.CaseRecommendDaoImpl;
import freecoding.service.CaseRecommendService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by loick on 17/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CaseRecommendDaoTest {

    Logger logger = Logger.getLogger(CaseRecommendDaoTest.class);


    @Autowired
    CaseRecommendDaoImpl caseRecommend;

    @Autowired
    CaseRecommendService caseRecommendService;




    @Test
    public void randomCaseTest(){
//        Assert.assertEquals(caseRecommend.getRandomCases().size(), 6);
//        System.out.println(caseRecommend.getRandomCases().get(0));
//        Document document=caseRecommend.getRandomCases().get(0);
//        Document cpfxgc=(Document) document.get("CPFXGC");
//        Document flftmc=(Document)cpfxgc.get("FLFTMC");
//        List<Document> l= (List<Document>) flftmc.get("TM");
//
//
//
//        System.out.println(l.get(0).get("@value"));
//        System.out.println(l.get(1).get("@value"));
//        System.out.println(l.get(2).get("@value"));



    }
}
