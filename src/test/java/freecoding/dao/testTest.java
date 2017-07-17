package freecoding.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zhujing on 2017/7/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class testTest {
    @Autowired
    private test t;

    @Test
    public void count() throws Exception {
        Assert.assertEquals(t.count(),5604);

    }

}