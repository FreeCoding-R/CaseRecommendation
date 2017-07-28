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
                "xml/C__Users_Administrator_Desktop_刑事二审案件_刑事二审案件_64.xml").toURI().getPath());
        System.out.println("use "+caseFile.getAbsolutePath()+" for test.");
        Assert.assertEquals(caseRecommendService.upload(caseFile), true);


//        Assert.assertEquals(caseRecommendService.init("5977d7e25979ae79d0aed2da"), true);
//        caseRecommendService.getCaseRecommendation();


    }

    @Test
//    @Ignore
    public void handleTest() throws DocumentException, FileContentException, ServiceProcessException {
        JSON result = caseRecommendService.handle();
        System.out.println(result.toString());
    }

    @Test
    @Ignore
    public void getCaseRecommendationTest() throws ServiceProcessException {
        List<Case> result = caseRecommendService.getCaseRecommendation();
//        Assert.assertNotNull(result);
//        Assert.assertTrue(result.size()>0);
//        Assert.assertNotNull(result.get(0));
//        Assert.assertNotNull(result.get(0).getId());
//        Assert.assertNotNull(result.get(0).getName());
        for (int i=0;i<result.size();i++){
            System.out.println(result.get(i).getId()+";"+result.get(i).getName());
        }
    }

    @Test
    @Ignore
    public void getLawDistributionTest() throws DocumentException, ServiceProcessException {
        List<Law> result = caseRecommendService.getLawDistribution();
//        Assert.assertNotNull(result);
//        Assert.assertTrue(result.size()>0);
//        Assert.assertNotNull(result.get(0));
//        Assert.assertNotNull(result.get(0).getName());
//        Assert.assertNotNull(result.get(0).getDetail());
//        Assert.assertNotNull(result.get(0).getNum());
        System.out.println(result.size());

        for (int i=0;i<result.size();i++){
            System.out.println(result.get(i).getName()+";"+result.get(i).getDetail()+";"+result.get(i).getNum());
        }
    }
}
