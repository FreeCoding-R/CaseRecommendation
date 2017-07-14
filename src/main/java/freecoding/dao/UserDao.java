package freecoding.dao;

import freecoding.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by zhujing on 2017/7/14.
 */
public interface UserDao extends JpaRepository<Person, Long> {




    @Query("from Person p where p.name =:name")
    List<Person> findByName(@Param(value = "name") String name);

}
