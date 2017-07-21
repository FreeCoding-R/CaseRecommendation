package freecoding.dao;

import freecoding.entity.Record;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by zhujing on 2017/7/21.
 */

//5971a3018623e308feb776bc
public interface RecordRepository extends MongoRepository<Record, String> {


    public List<Record> findByEmail(String email);


}
