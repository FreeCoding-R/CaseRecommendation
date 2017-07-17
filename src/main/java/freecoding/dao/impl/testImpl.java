package freecoding.dao.impl;

import freecoding.dao.test;
import org.springframework.stereotype.Repository;
import freecoding.util.MongoData;

/**
 * Created by zhujing on 2017/7/17.
 */
@Repository
public class testImpl implements test {

    @Override
    public long count() {
        return MongoData.getDataBase().getCollection("traffic").count();
    }
}
