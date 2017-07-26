package freecoding.dao;

import freecoding.dao.impl.CaseRecommendDaoImpl;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by loick on 17/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CaseRecommendDaoTest {

    Logger logger = Logger.getLogger(CaseRecommendDaoTest.class);


    @Autowired
    CaseRecommendDaoImpl caseRecommendDao;

    @Test
    public void randomCaseTest() throws IOException, DocumentException, URISyntaxException {
        //getClass().getClassLoader().getResource("")可以获得运行时项目根路径，
        // 如在这里运行获得的就是"你的电脑的路径/CaseRecommendation/target/test-classes/"
        // 用.toURI()解决中文名乱码的问题
//        File myXML = new File(getClass().getClassLoader().getResource(
//                "xml/(2011)宝刑初字第0258号刑事判决书（一审公诉案件适用普通程序用）.doc.xml").toURI().getPath());
//        SAXReader sr = new SAXReader();
//        Document document = sr.read(myXML);
//        String responseTextObj = document.asXML();
//        XMLSerializer xmlSerializer = new XMLSerializer();

//        List<org.bson.Document> documentList = caseRecommendDao.getRandomCases();
//        Assert.assertEquals(6, documentList.size());



    }

    @Test
    public void getKeyTest(){
        Assert.assertNotNull(caseRecommendDao.getRandomCases().size());
    }

    @Test
    public void findTest() {

    }
}
