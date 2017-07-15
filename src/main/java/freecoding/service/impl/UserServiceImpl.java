package freecoding.service.impl;

import freecoding.dao.UserDao;
import freecoding.entity.Person;
import freecoding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by zhujing on 2017/7/14.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userManage;

    @Override
    public List<Person> search(String name) {
        return userManage.findByName(name);
    }
}
