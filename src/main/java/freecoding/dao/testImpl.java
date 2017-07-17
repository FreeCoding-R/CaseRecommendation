package freecoding.dao;

import org.springframework.stereotype.Service;
import preData.MongoData;

/**
 * Created by zhujing on 2017/7/17.
 */
@Service
public class testImpl implements test {

    @Override
    public long count() {
        return MongoData.getDataBase().getCollection("traffic").count();
    }
}
