package freecoding.dao;

import freecoding.dao.impl.CaseRecommendDaoImpl;
import net.sf.json.xml.XMLSerializer;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

/**
 * Created by loick on 17/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CaseRecommendDaoTest {

    Logger logger = Logger.getLogger(CaseRecommendDaoTest.class);


    @Autowired
    CaseRecommendDaoImpl caseRecommend;

    @Test
    public void randomCaseTest() throws IOException, DocumentException {

        File myXML = new File("/Users/zhujin/Downloads/天津文书交通肇事和危险驾驶分案由子集(冯奕2017:4:1)/危险驾驶罪/(2011)宝刑初字第0258号刑事判决书（一审公诉案件适用普通程序用）.doc.xml");
        SAXReader sr = new SAXReader();
        Document document =sr.read(myXML);
        String responseTextObj = document.asXML();
        XMLSerializer xmlSerializer = new XMLSerializer();


    }
}
