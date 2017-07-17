package freecoding.dao;

import freecoding.dao.impl.CaseRecommendImpl;
import org.junit.Assert;
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
    @Autowired
    CaseRecommendImpl caseRecommend;

    @Test
    public void randomCaseTest(){
        Assert.assertEquals(caseRecommend.getRandomCases().size(), 6);
    }
}
