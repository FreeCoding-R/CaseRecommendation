package freecoding.dao;

import freecoding.dao.impl.CaseRecommendDaoImpl;
import freecoding.util.MongodbUtil;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.URISyntaxException;

/**
 * Created by loick on 17/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CaseRecommendDaoTest {

    Logger logger = Logger.getLogger(CaseRecommendDaoTest.class);

    @Autowired
    MongodbUtil mongodbUtil;

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
    @Ignore
    public void testKmeans() throws URISyntaxException {
        File file = new File(getClass().getClassLoader().getResource(
                "xml/C__Users_Administrator_Desktop_刑事二审案件_刑事二审案件_151160.xml").toURI().getPath());
        Assert.assertEquals(6, caseRecommendDao.getKmeansCases(file).size());
    }

    @Test
    public void testSecondRecommend(){
        Assert.assertEquals(6, caseRecommendDao.getKmeansCases(1).size());
    }



    @Test
    public void testMongo(){
        Assert.assertNotNull(mongodbUtil.getDatabase().getName(), "freecoding");
    }

    @Test
    public void testRandom(){
        Assert.assertEquals(6, caseRecommendDao.getRandomCases().size());
    }

    @Test
    public void testProcess(){
        String cp = "src/main/python2.0/query.py";
        String data = "";
        String file = "userFiles/test.xml";
        Runtime runtime = Runtime.getRuntime();
        try {
            System.out.println();

            Process process = runtime.exec("python3"+" " + cp+" "+file);
            //Process process2 = Runtime.getRuntime().exec("python3"+" " + cp+" "+file);
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = reader.readLine()) != null){
                data+=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(data);
    }

}
