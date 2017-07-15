package freecoding.dao;

import freecoding.entity.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by zhujing on 2017/7/15.
 */
public interface PersonRepository extends MongoRepository<Person,Integer> {


    List<Person> findByName(String name);

    Person findById(int id);

    List<Person> findByAge(int age);

}
