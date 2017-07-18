package freecoding.dao;

import freecoding.dao.impl.CaseRecommendImpl;
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
    CaseRecommendImpl caseRecommend;




    @Test
    public void randomCaseTest(){
//        Assert.assertEquals(caseRecommend.getRandomCases().size(), 6);
//        System.out.println(caseRecommend.getRandomCases().get(0));
//        Document document=caseRecommend.getRandomCases().get(0);



    }
}
