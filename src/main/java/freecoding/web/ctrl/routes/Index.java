package freecoding.web.ctrl.routes;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zjy on 2017/7/15.
 */
@RestController
@EnableAutoConfiguration
public class Index {

    @RequestMapping(value="/", method= RequestMethod.GET)
    String home() {
        return "Hello FreeCoding!";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    String user(@RequestParam(value = "name", required = false) String name,
                @RequestParam(value = "pwd", required = false) String pwd) {
        return "Hello "+name;
    }
}
