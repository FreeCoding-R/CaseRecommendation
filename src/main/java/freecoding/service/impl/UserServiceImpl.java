package freecoding.service.impl;

import freecoding.dao.CaseRecommendDao;
import freecoding.dao.UserRepository;
import freecoding.entity.User;
import freecoding.exception.ServiceProcessException;
import freecoding.service.UserService;
import freecoding.util.MongodbUtil;
import freecoding.vo.Case;
import org.bson.Document;
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
    private UserRepository userRepository;

    @Autowired
    private CaseRecommendDao caseRecommendDao;


    @Override
    public boolean login(String email, String password) {
        User u=userRepository.findByEmail(email);
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
        User u=userRepository.findByEmail(user.getEmail());
        if(u!=null){
            return false;
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public User show(String email) throws ServiceProcessException {
        User u=userRepository.findByEmail(email);
        if(u==null){
            throw  new ServiceProcessException("未按顺序调用或错误传参");
        }
        return u;
    }

    @Override
    public boolean modify(User user) throws ServiceProcessException {
        if (userRepository.findByEmail(user.getEmail())==null){
            throw  new ServiceProcessException("未按顺序调用或错误传参");
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public List<Case> getCaseListByUser(String email) {
        List<Case> result=new ArrayList<>();

        List<Document> l=caseRecommendDao.findByUser(email);
        for (int i = 0; i < l.size(); i++) {
            Case c=new Case();
            Document document=l.get(i);
            c.setId(document.get("_id").toString());
            c.setName(document.get("name").toString());
            result.add(c);

        }

        return result;
    }

    @Override
    public boolean insert(File file, String email) throws DocumentException {

        MongodbUtil.insert(file,email);
        return true;
    }

    @Override
    public List<String> getNewestCaseList() {
        return null;
    }
}
