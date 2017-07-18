package freecoding.web.ctrl.routes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Pattern;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by zjy on 2017/7/18.
 */
/*
下面的注释已经过时，现在已经替换
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CaserecommendationApplication.class)
@WebIntegrationTest("server.port:0")// 使用0表示端口号随机，也可以具体指定如8888这样的固定端口
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexTest {
    private String dateReg;
    private Pattern pattern;
    private TestRestTemplate template = new TestRestTemplate();
    @Value("${local.server.port}")// 注入端口号
    private int port;

    @Test
    public void testHome(){
        String url = "http://localhost:"+port+"/";
        String result = template.getForObject(url, String.class);
        assertNotNull(result);
        assertTrue(result.startsWith("<!DOCTYPE html>"));
    }

    @Test
    public void testPerson(){
        String url = "http://localhost:"+port+"/person";
        String result = template.getForObject(url, String.class);
        assertNotNull(result);
        assertTrue(result.startsWith("<!DOCTYPE html>"));
    }
}
