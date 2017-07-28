package freecoding.dao;

import freecoding.entity.Record;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by zhujing on 2017/7/28.
 */
public interface RecordRepository extends MongoRepository<Record, String> {

    public List<Record> findByUserName(String userName);
}
