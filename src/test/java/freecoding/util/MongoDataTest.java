package freecoding.util;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 铠联 on 2017/7/19.
 */
public class MongoDataTest {
    @Autowired
    private MongodbUtil mongoData;



    @Test
    public void getDataBaseTest(){
        Assert.assertNotNull(mongoData.getDatabase());
    }

}
