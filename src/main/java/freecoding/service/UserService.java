package freecoding.service;

import freecoding.entity.Person;

import java.util.List;

/**
 * Created by zhujing on 2017/7/14.
 */
public interface UserService {

    List<Person> search(String name);
}
