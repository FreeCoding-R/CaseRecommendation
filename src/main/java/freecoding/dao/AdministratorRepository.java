package freecoding.dao;

import freecoding.entity.Administrator;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by zhujing on 2017/7/21.
 */
public interface AdministratorRepository extends MongoRepository<Administrator, String> {


    public Administrator findByUserName(String userName);


}
