package freecoding.service.impl;

import freecoding.dao.AdministratorRepository;
import freecoding.entity.Administrator;
import freecoding.service.AdminService;
import freecoding.vo.Case;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public List<Case> geRecordListByAdmin(Date start, Date end) {
        return null;
    }
}
