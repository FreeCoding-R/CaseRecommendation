package freecoding.service.impl;

import freecoding.dao.AdministratorRepository;
import freecoding.entity.Administrator;
import freecoding.entity.User;
import freecoding.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhujing on 2017/7/21.
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdministratorRepository administratorRepository;


    @Override
    public boolean login(String userName, String password) {
        Administrator u=administratorRepository.findByUserName(userName);
        if(u==null){
            return false;
        }
        if(!u.getPassword().equals(password)){
            return false;
        }

        return true;
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(String email) {
        return false;
    }

    @Override
    public List<User> getAllUser() {
        return null;
    }


}
