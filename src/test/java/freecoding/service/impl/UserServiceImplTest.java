package freecoding.service.impl;

import freecoding.service.UserService;
import org.junit.Assert;
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


    @Test
    public void search() throws Exception {
//        Assert.assertEquals((int)userService.search("zhujing").get(0).getAge(),21);




        
    }

}