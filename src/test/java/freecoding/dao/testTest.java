package freecoding.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhujing on 2017/7/17.
 */
public class testTest {
    @Autowired
    private test t;

    @Test
    public void count() throws Exception {
        Assert.assertEquals(t.count(),5604);

    }

}