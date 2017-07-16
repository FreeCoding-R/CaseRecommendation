package freecoding.dao;

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
        
    }

    @Test
    public void findById() throws Exception {

    }

    @Test
    public void findByAge() throws Exception {


    }

}