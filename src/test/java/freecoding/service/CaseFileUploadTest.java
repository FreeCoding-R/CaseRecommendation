package freecoding.service;

import freecoding.exception.FileContentException;
import freecoding.exception.ServiceProcessException;
import freecoding.service.impl.CaseRecommendServiceImpl;
import freecoding.vo.Case;
import freecoding.vo.Law;
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

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;


/**
 * Created by zjy on 2017/7/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CaseFileUploadTest {
    @Autowired
    private CaseRecommendServiceImpl caseRecommendService;

    @Test
    @Before
    public void caseFileUploadTest() throws URISyntaxException, DocumentException, ServiceProcessException, FileContentException {
        //getClass().getClassLoader().getResource("")可以获得运行时项目根路径，
        // 如在这里运行获得的就是"你的电脑的路径/CaseRecommendation/target/test-classes/"
        // 用.toURI()解决中文名乱码的问题
        File caseFile = new File(getClass().getClassLoader().getResource(
                "xml/(2016)津0225刑初747号刑事判决书(一审公诉案件适用简易程序用).doc.xml").toURI().getPath());
        System.out.println("use "+caseFile.getAbsolutePath()+" for test.");
        Assert.assertEquals(caseRecommendService.upload(caseFile), true);
//        Assert.assertEquals(caseRecommendService.init("596f05afec393fe8aab835ec"), true);
//        caseRecommendService.handle();

    }

    @Test
//    @Ignore
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
        JSON result = caseRecommendService.detail("诉讼参与人/王占双");
        System.out.println(result.toString());
    }

    @Test
    @Ignore
    public void getCaseRecommendationTest(){
        List<Case> result = caseRecommendService.getCaseRecommendation();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.size()>0);
        Assert.assertNotNull(result.get(0));
        Assert.assertNotNull(result.get(0).getId());
        Assert.assertNotNull(result.get(0).getName());
    }

    @Test
    @Ignore
    public void getLawDistributionTest(){
        List<Law> result = caseRecommendService.getLawDistribution();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.size()>0);
        Assert.assertNotNull(result.get(0));
        Assert.assertNotNull(result.get(0).getName());
        Assert.assertNotNull(result.get(0).getDetail());
        Assert.assertNotNull(result.get(0).getNum());
    }
}
