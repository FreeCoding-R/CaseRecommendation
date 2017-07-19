package freecoding.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by 铠联 on 2017/7/19.
 */
public class MongoDataTest {

    private MongoData mongoData;

    @Before
    public void init(){
        mongoData = new MongoData();
    }

    @Test
    public void getDataBaseTest(){
        Assert.assertNotNull(mongoData.getDataBase());
    }

}
