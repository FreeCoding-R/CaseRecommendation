package freecoding.service.impl;

import freecoding.dao.CaseRecommendDao;
import freecoding.dao.RecordRepository;
import freecoding.dao.UserRepository;
import freecoding.entity.Record;
import freecoding.entity.User;
import freecoding.exception.ServiceProcessException;
import freecoding.service.UserService;
import freecoding.util.MongodbUtil;
import freecoding.vo.Case;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhujing on 2017/7/21.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MongodbUtil mongodbUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CaseRecommendDao caseRecommendDao;

    @Autowired
    private RecordRepository recordRepository;


    @Override
    public boolean login(String userName, String password) {
        User u=userRepository.findByUserName(userName);
        if(u==null){
            return false;
        }
        if(!u.getPassword().equals(password)){
            return false;
        }
        return true;
    }

    @Override
    public boolean signUp(User user) {
        if(user.getUserName()==null||user.getPassword()==null){
            return false;
        }
        User u=userRepository.findByUserName(user.getUserName());
        if(u!=null){
            return false;
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public User show(String userName) throws ServiceProcessException {
        User u=userRepository.findByUserName(userName);
        if(u==null){
            throw  new ServiceProcessException("未按顺序调用或错误传参");
        }
        return u;
    }

    @Override
    public boolean modify(User user) throws ServiceProcessException {
        if (userRepository.findByUserName(user.getUserName())==null){
            throw  new ServiceProcessException("未按顺序调用或错误传参");
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public List<Case> getCaseListByUser(String userName) throws ServiceProcessException {
        if(userRepository.findByUserName(userName)==null){
            throw  new ServiceProcessException("未按顺序调用或错误传参");
        }

        List<Case> result=new ArrayList<>();
        List<Record> l=recordRepository.findByUserName(userName);

        for (int i = 0; i < l.size(); i++) {
            Case c=new Case();
            Record record=l.get(i);
            c.setId(record.getFileId());
            c.setName(record.getFileName());
            result.add(c);
        }
        return result;
    }


    @Override
    public boolean insert(File file, String userName) throws DocumentException, ServiceProcessException {
        //不再对file做详细检查
        if(userRepository.findByUserName(userName)==null||file==null){
            throw  new ServiceProcessException("未按顺序调用或错误传参");
        }
        mongodbUtil.insert(file,userName);
        return true;
    }

    @Override
    public List<String> getNewestCaseList() {
        return null;
    }
}
