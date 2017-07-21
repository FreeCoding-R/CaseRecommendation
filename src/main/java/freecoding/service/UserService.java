package freecoding.service;

import freecoding.entity.User;
import freecoding.vo.Case;

import java.util.List;

/**
 * Created by zhujing on 2017/7/17.
 */
public interface UserService {


    public boolean login(String email,String password);


    public boolean signUp(User user);


    public User show(String userId);


    public boolean modify(User user);


    public List<Case> getCaseListByUser();


}
