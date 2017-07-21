package freecoding.service;

import freecoding.vo.Case;

import java.util.List;

/**
 * Created by zhujing on 2017/7/21.
 */
public interface AdminService {

    public boolean admin();

    public List<Case> geCaseListByAdmin();

}
