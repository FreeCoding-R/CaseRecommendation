package freecoding.service.impl;

import freecoding.dao.UserDao;
import freecoding.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zhujing on 2017/7/14.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;


    @Test
    public void search() throws Exception {

//        Assert.assertEquals((int)userService.search("zhujing").get(0).getAge(),23);

//        Person p=new Person();
//        p.setName("zhujing");
//        p.setAge(23);
//

//        Logger logger = LoggerFactory.getLogger(this.getClass());
//        logger.debug("print debug log.");
//        logger.info("print info log.");
//        logger.error("print error log.");
//        logger.warn("print warn log.");
//







    }

}