package freecoding.dao;

import preData.MongoData;

/**
 * Created by zhujing on 2017/7/17.
 */
public class testImpl implements test {
    @Override
    public long count() {
        return MongoData.getDataBase().getCollection("traffic").count();
    }
}
