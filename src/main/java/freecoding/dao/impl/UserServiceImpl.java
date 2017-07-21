package freecoding.dao.impl;

import freecoding.dao.RecordRepository;
import freecoding.dao.UserRepository;
import freecoding.entity.Record;
import freecoding.entity.User;
import freecoding.exception.ServiceProcessException;
import freecoding.service.UserService;
import freecoding.vo.Case;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private RecordRepository recordRepository;


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
    public List<String> getCaseListByUser(String email) {
        List<String> result=new ArrayList<>();
        List l=recordRepository.findByEmail(email);

        for (int i = 0; i < l.size(); i++) {
            Record r= (Record) l.get(i);
            result.add(r.getCaseName());
        }

        return result;
    }

    @Override
    public boolean insert() {
        return false;
    }
}
