package freecoding.service;

import freecoding.entity.User;
import freecoding.exception.ServiceProcessException;
import freecoding.vo.Case;
import org.dom4j.DocumentException;

import java.io.File;
import java.util.List;

/**
 * Created by zhujing on 2017/7/17.
 */
public interface UserService {


    /**
     *  登录
     * @param email
     * @param password
     * @return
     */
    public boolean login(String email,String password);


    /**
     *  注册
     * @param user
     * @return
     */
    public boolean signUp(User user);


    /**
     *  个人信息展示
     * @param email
     * @return
     * @throws ServiceProcessException
     */
    public User show(String email) throws ServiceProcessException;


    /**
     * 个人信息修改
     * @param user
     * @return
     * @throws ServiceProcessException
     */
    public boolean modify(User user) throws ServiceProcessException;


    /**
     *  用户已上传文书列表
     * @param email
     * @return
     */
    public List<Case> getCaseListByUser(String email);


    /**
     *  用户上传文书存储
     * @return
     */
    public boolean insert(File file, String email) throws DocumentException;


    /**
     *
     * 展示所有最新上传文书
     * @return
     */
    public List<String> getNewestCaseList();


}
