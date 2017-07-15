package freecoding.dao;

import freecoding.entity.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhujing on 2017/7/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;


    @Test
    public void findByName() throws Exception {
        personRepository.save(new Person(2,"zhujunyi",22));
        personRepository.save(new Person(3,"tianguison",20));

    }

    @Test
    public void findById() throws Exception {

    }

    @Test
    public void findByAge() throws Exception {
        Assert.assertEquals(personRepository.findByAge(21).get(0).getName(),"zhujing");

    }

}