package freecoding.service.impl;

import freecoding.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zhujing on 2017/7/25.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;


    @Test
    public void login() throws Exception {

//        File file=new File("/Users/zhujin/Downloads/3组/训练集/C__Users_Administrator_Desktop_刑事二审案件_刑事二审案件_9913.xml");
//        userService.insert(file,"test");
////        System.out.println(userService.getCaseListByUser("test").get(0).getName());
//        List l=userService.getCaseListByUser("test");
//        System.out.println(l.size());
//        Case record= (Case) l.get(0);
//        System.out.println(record.getId()+record.getName());
    }

}