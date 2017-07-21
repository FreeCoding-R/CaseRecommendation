package freecoding.entity;

import org.springframework.data.annotation.Id;

/**
 * Created by zhujing on 2017/7/21.
 */
public class Administrator {
    @Id
    private String userName;

    private String password;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
