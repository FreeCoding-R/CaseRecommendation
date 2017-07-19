package freecoding.web.ctrl.routes;

import freecoding.web.data.model.Person;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjy on 2017/7/15.
 */
@EnableAutoConfiguration
@Controller
public class Index {

    @RequestMapping(value="/",method= RequestMethod.GET)
    public String home() {
        return "index";
    }

    @RequestMapping(value="/index", method= RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value="/case", method= RequestMethod.GET)
    public String toCase() {
        return "case";
    }

    @RequestMapping("/person")
    public String index(Model model){
        Person single=new Person("aa",1);
        List<Person> people=new ArrayList<Person>();
        Person p1=new Person("bb",2);
        Person p2=new Person("cc",3);
        Person p3=new Person("dd",4);
        people.add(p1);
        people.add(p2);
        people.add(p3);
        model.addAttribute("singlePerson",single);
        model.addAttribute("people",people);
        return "person";
    }
}
