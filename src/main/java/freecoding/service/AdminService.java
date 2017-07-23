package freecoding.service;

import java.util.Date;
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
     * 管理员按日期查看用户上传记录
     * @param start
     * @param end
     * @return
     */
    public List<Record> geRecordListByAdmin(Date start,Date end);

}
