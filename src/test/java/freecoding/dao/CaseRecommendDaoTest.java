package freecoding.dao;

import freecoding.dao.impl.CaseRecommendDaoImpl;
import net.sf.json.xml.XMLSerializer;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

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
        File myXML = new File(getClass().getClassLoader().getResource(
                "xml/(2011)宝刑初字第0258号刑事判决书（一审公诉案件适用普通程序用）.doc.xml").toURI().getPath());
        SAXReader sr = new SAXReader();
        Document document = sr.read(myXML);
        String responseTextObj = document.asXML();
        XMLSerializer xmlSerializer = new XMLSerializer();

        List<org.bson.Document> documentList = caseRecommendDao.getRandomCases();
        Assert.assertEquals(6, documentList.size());
    }

    @Test
    public void findTest() {
        org.bson.Document document = caseRecommendDao.find("596b2195ec393fc6ed998155");
        Assert.assertNotNull(document);
        Assert.assertEquals("Document{{@value=天津市南开区人民法院 刑事判决书 (2001)南刑初字第125号, @nameCN=文首," +
                " WSZZDW=Document{{@value=法院, @nameCN=文书制作单位}}, JBFY=Document{{@value=天津市南开区人民法院, " +
                "@nameCN=经办法院, FYJB=Document{{@value=基层, @nameCN=法院级别}}, XZQH_P=Document{{@value=天津, @nameCN=行政区划(省)}}}}, " +
                "WSMC=Document{{@value=刑事判决书, @nameCN=文书名称}}, AH=Document{{@value=(2001)南刑初字第125号, @nameCN=案号}}, " +
                "LAND=Document{{@value=2001, @nameCN=立案年度}}, AJXZ=Document{{@value=刑事案件, @nameCN=案件性质}}, " +
                "WSZL=Document{{@value=判决书, @nameCN=文书种类}}, SPCX=Document{{@value=一审案件, @nameCN=审判程序}}, " +
                "AJLX=Document{{@value=刑事一审案件, @nameCN=案件类型}}}}",String.valueOf(document.get("WS")));
        System.out.println(String.valueOf(document.get("WS")));
    }
}
