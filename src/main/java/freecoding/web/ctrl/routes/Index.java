package freecoding.web.ctrl.routes;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zjy on 2017/7/15.
 */
@EnableAutoConfiguration
@Controller
public class Index {

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String home() {
        return "views/index.html";
    }

}
