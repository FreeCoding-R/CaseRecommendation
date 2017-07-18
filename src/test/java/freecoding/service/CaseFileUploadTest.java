package freecoding.service;

import freecoding.service.impl.CaseRecommendServiceImpl;
import net.sf.json.JSON;
import org.dom4j.DocumentException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

/**
 * Created by zjy on 2017/7/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CaseFileUploadTest {
    @Autowired
    private CaseRecommendServiceImpl caseRecommendService;

    @Test
    public void caseFileUploadTest(){
        //getClass().getClassLoader().getResource("")可以获得运行时项目根路径，
        // 如在这里运行获得的就是"你的电脑的路径/CaseRecommendation/target/test-classes/"
        File caseFile = new File(getClass().getClassLoader().getResource("xml/(2016)津0225刑初747号刑事判决书(一审公诉案件适用简易程序用).doc.xml").getPath());
        System.out.println("use "+caseFile.getAbsolutePath()+" for test.");
        Assert.assertEquals(caseRecommendService.upload(caseFile), true);
    }
}
