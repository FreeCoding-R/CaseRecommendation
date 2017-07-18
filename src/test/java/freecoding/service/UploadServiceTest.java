package freecoding.service;

import freecoding.service.impl.CaseRecommendServiceImpl;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

/**
 * Created by zjy on 2017/7/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UploadServiceTest {
    @Autowired
    private CaseRecommendServiceImpl caseRecommendService;

    @Test
    public void nullFileUploadTest(){
        Assert.assertEquals(caseRecommendService.upload(null), false);
    }

    @Test
    public void normalFileUploadTest(){
        //getClass().getClassLoader().getResource("")可以获得运行时项目根路径，
        // 如在这里运行获得的就是"你的电脑的路径/CaseRecommendation/target/test-classes/"
        File scores = new File(getClass().getClassLoader().getResource("xml/scores.xml").getPath());
        System.out.println("use "+scores.getAbsolutePath()+" for test.");
        Assert.assertEquals(caseRecommendService.upload(scores), true);

        File employee = new File(getClass().getClassLoader().getResource("xml/employee.xml").getPath());
        System.out.println("use "+employee.getAbsolutePath()+" for test.");
        Assert.assertEquals(caseRecommendService.upload(employee), true);
    }

    @Test
    public void caseFileUploadTest(){
        //getClass().getClassLoader().getResource("")可以获得运行时项目根路径，
        // 如在这里运行获得的就是"你的电脑的路径/CaseRecommendation/target/test-classes/"
        File caseFile = new File(getClass().getClassLoader().getResource("xml/(2016)津0225刑初747号刑事判决书(一审公诉案件适用简易程序用).doc.xml").getPath());
        System.out.println("use "+caseFile.getAbsolutePath()+" for test.");
        Assert.assertEquals(caseRecommendService.upload(caseFile), true);
    }

    /**
     * 创建一个xml文件
     * @param fileName 文件路径（文件名），当前路径是项目根路径，
     *                 如"scores.xml"是"你的电脑的路径/CaseRecommendation/scores.xml"
     */
    public void createXml(String fileName) {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        }

        Element root = document.createElement("scores");
        root.setAttribute("value","60");
        document.appendChild(root);
        Element employee = document.createElement("employee");
        Element name = document.createElement("name");
        name.appendChild(document.createTextNode("wangchenyang"));
        employee.appendChild(name);
        Element sex = document.createElement("sex");
        sex.appendChild(document.createTextNode("male"));
        employee.appendChild(sex);
        Element age = document.createElement("age");
        age.appendChild(document.createTextNode("26"));
        employee.appendChild(age);
        root.appendChild(employee);

        Element employee2 = document.createElement("employee");
        Element name2 = document.createElement("name");
        name2.appendChild(document.createTextNode("xuxin"));
        employee2.appendChild(name2);
        Element sex2 = document.createElement("sex");
        sex2.appendChild(document.createTextNode("female"));
        employee2.appendChild(sex2);
        Element age2 = document.createElement("age");
        age2.appendChild(document.createTextNode("25"));
        employee2.appendChild(age2);
        root.appendChild(employee2);

        TransformerFactory tf = TransformerFactory.newInstance();
        try {
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
            StreamResult result = new StreamResult(pw);
            transformer.transform(source, result);
            System.out.println("生成XML文件成功!");
        } catch (TransformerConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (TransformerException e) {
            System.out.println(e.getMessage());
        }
    }
}
