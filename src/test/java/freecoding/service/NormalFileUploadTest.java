package freecoding.service;

import freecoding.exception.ServiceProcessException;
import freecoding.vo.Case;
import freecoding.vo.Law;
import freecoding.exception.FileContentException;
import freecoding.service.impl.CaseRecommendServiceImpl;
import net.sf.json.JSON;
import org.dom4j.DocumentException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by zjy on 2017/7/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NormalFileUploadTest {
    @Autowired
    private CaseRecommendServiceImpl caseRecommendService;

    @Test
    @Before
    public void normalFileUploadTest(){
        //getClass().getClassLoader().getResource("")可以获得运行时项目根路径，
        // 如在这里运行获得的就是"你的电脑的路径/CaseRecommendation/target/test-classes/"
        File employee = new File(getClass().getClassLoader().getResource("xml/employee.xml").getPath());
        System.out.println("use "+employee.getAbsolutePath()+" for test.");
        Assert.assertEquals(caseRecommendService.upload(employee), true);
    }

    @Test
    @Ignore
    public void handleTest() throws DocumentException, FileContentException, ServiceProcessException {
        JSON result = caseRecommendService.handle();
        System.out.println(result.toString());
    }

    @Test(expected=DocumentException.class)
    @Ignore
    public void nullKeywordDetailTest() throws DocumentException, FileContentException, ServiceProcessException {
        caseRecommendService.detail(null);
    }

    @Test
    @Ignore
    public void normalKeywordDetailTest() throws FileContentException, DocumentException, ServiceProcessException {
        JSON result = caseRecommendService.detail("employee");
        Assert.assertEquals(result.toString(), "");
    }

    @Test
    @Ignore
    public void getCaseRecommendationTest() throws ServiceProcessException {
        List<Case> result = caseRecommendService.getCaseRecommendation();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.size()>0);
        Assert.assertNotNull(result.get(0));
        Assert.assertNotNull(result.get(0).getId());
        Assert.assertNotNull(result.get(0).getName());
    }

    @Test
    @Ignore
    public void getLawDistributionTest() throws DocumentException, ServiceProcessException {
        List<Law> result = caseRecommendService.getLawDistribution();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.size()>0);
        Assert.assertNotNull(result.get(0));
        Assert.assertNotNull(result.get(0).getName());
        Assert.assertNotNull(result.get(0).getDetail());
        Assert.assertNotNull(result.get(0).getNum());
    }

    /**
     * 创建一个xml文件
     * @param fileName 文件路径（文件名），当前路径是项目根路径，
     *                 如"scores.xml"是"你的电脑的路径/CaseRecommendation/scores.xml"
     */
    private void createXml(String fileName) {
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
