package freecoding.web.ctrl.routes;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by zjy on 2017/7/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {
    private String dateReg;
    private Pattern pattern;
    private TestRestTemplate template = new TestRestTemplate();
    @Value("${local.server.port}")// 注入端口号
    private int port;

    @Test
    @Ignore
    public void testUser(){
        String url = "http://localhost:"+port+"/user";
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("name", "Tom");
        map.add("pwd", "Lily");
        String result = template.postForObject(url, map, String.class);
        assertNotNull(result);
        assertEquals(result,"Hello Tom");
    }
}
