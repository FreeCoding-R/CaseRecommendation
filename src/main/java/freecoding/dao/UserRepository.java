package freecoding.dao;

import freecoding.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by zhujing on 2017/7/21.
 */
public interface UserRepository extends MongoRepository<User, String> {


    public User findByEmail(String email);



}
