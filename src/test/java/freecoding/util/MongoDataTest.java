package freecoding.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by 铠联 on 2017/7/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDataTest {
    @Autowired
    private MongodbUtil mongoData;



    @Test
    public void getDataBaseTest(){
        Assert.assertNotNull(mongoData.getDatabase());
    }

}
