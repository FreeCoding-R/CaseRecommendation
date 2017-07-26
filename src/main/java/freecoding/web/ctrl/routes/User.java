package freecoding.web.ctrl.routes;

import freecoding.web.ctrl.security.WebSecurityConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zjy on 2017/7/16.
 */
@EnableAutoConfiguration
@Controller
public class User {

    @RequestMapping(value = "/user")
    public @ResponseBody String user(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "pwd", required = false) String pwd) {
        return "Hello " + name;
    }

    @RequestMapping(value = "/user/name", method= RequestMethod.GET)
    public @ResponseBody String userName(@SessionAttribute(WebSecurityConfig.SESSION_KEY) String username, Model model) {
        return "user: " + username;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/loginPost")
    public String loginPost(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password,
            HttpSession session, RedirectAttributesModelMap modelMap) {

        //防止重复登录
        if(session.getAttribute(WebSecurityConfig.SESSION_KEY)!=null){
            modelMap.addFlashAttribute("alertType","alert-warning");
            modelMap.addFlashAttribute("alertMessage","请勿重复登录！");
            return "redirect:/";
        }

        //密码验证
        if (!"123456".equals(password)) {
            modelMap.addFlashAttribute("alertType","alert-danger");
            modelMap.addFlashAttribute("alertMessage","密码错误");
            return "redirect:/login";
        }

        // 设置session
        session.setAttribute(WebSecurityConfig.SESSION_KEY, username);

        modelMap.addFlashAttribute("alertType","alert-success");
        modelMap.addFlashAttribute("alertMessage","登录成功");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 移除session
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:/";
    }

}
