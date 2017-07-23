package freecoding.service;

import freecoding.entity.User;

import java.util.List;

/**
 * Created by zhujing on 2017/7/21.
 */
public interface AdminService {

    /**
     * 管理员登录
     * @param userName
     * @param password
     * @return
     */

    public boolean login(String userName,String password);


    /**
     *
     * @param user
     * @return
     */
    public boolean addUser(User user);

    /**
     *
     * @param email
     * @return
     */
    public boolean deleteUser(String email);

    /**
     *
     * @return
     */
    public List<User> getAllUser();

}
