package freecoding.web.ctrl.routes;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zjy on 2017/7/16.
 */
@EnableAutoConfiguration
@RestController
public class User {

    @RequestMapping(value = "/user")
    public String user(@RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "pwd", required = false) String pwd) {
        int i = 2/0;
        return "Hello " + name;
    }
}
