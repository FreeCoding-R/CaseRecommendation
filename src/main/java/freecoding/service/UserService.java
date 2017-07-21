package freecoding.service;

import freecoding.entity.User;
import freecoding.exception.ServiceProcessException;

import java.util.List;

/**
 * Created by zhujing on 2017/7/17.
 */
public interface UserService {


    public boolean login(String email,String password);


    public boolean signUp(User user);


    public User show(String email) throws ServiceProcessException;


    public boolean modify(User user) throws ServiceProcessException;


    public List<String> getCaseListByUser(String email);


    public boolean insert();


}
