package freecoding.dao;

import freecoding.dao.impl.CaseRecommendDaoImpl;
import freecoding.util.MongoData;
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
    CaseRecommendDaoImpl caseRecommend;

    @Test
    public void randomCaseTest() throws IOException, DocumentException, URISyntaxException {
        //getClass().getClassLoader().getResource("")可以获得运行时项目根路径，
        // 如在这里运行获得的就是"你的电脑的路径/CaseRecommendation/target/test-classes/"
        // 用.toURI()解决中文名乱码的问题
//        File myXML = new File(getClass().getClassLoader().getResource(
//                "xml/(2011)宝刑初字第0258号刑事判决书（一审公诉案件适用普通程序用）.doc.xml").toURI().getPath());
//        SAXReader sr = new SAXReader();
//        Document document =sr.read(myXML);
//        String responseTextObj = document.asXML();
//        XMLSerializer xmlSerializer = new XMLSerializer();
//        Document document=null;
//        try {
//            document=caseRecommend.find("596b2195ec393fc6ed998155");
//        }catch (Exception e){
//            System.out.println(false);
//        }
//
//
//        String a=document.toJson();
//        a = a.replace("\n", "\\n");
//        a = a.replace("\r", "\\r");
//        a=a.substring(51);
//
//        a="{\"QW\":{"+a+"}";
//        System.out.println(a);
//        XMLSerializer serializer = new XMLSerializer();
//        JSON jsonObject = JSONSerializer.toJSON(a);
//        String h=serializer.write(jsonObject);
//        h=h.substring(43,h.length()-6);
////        System.out.println(h);
//
////        System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+h.substring(43,h.length()-6));
//
////
////        System.out.println(xmlSerializer.read(a).toString());
////        fileWriter.write(a);
//        org.dom4j.Document d= DocumentHelper.parseText(h);
//        System.out.println(d.getRootElement().attribute("value").getText());

    }

    @Test
    public void keyTest(){
        int len = caseRecommend.getKeyCases(MongoData.getDataBase().getCollection("traffic").find().first()).size();
        Assert.assertEquals(len, 6);
    }
}
