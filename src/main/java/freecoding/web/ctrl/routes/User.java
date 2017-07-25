package freecoding.web.ctrl.routes;

import freecoding.web.ctrl.security.WebSecurityConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public @ResponseBody Map<String, Object> loginPost(String username, String password, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if (!"123456".equals(password)) {
            map.put("success", false);
            map.put("message", "密码错误");
            return map;
        }

        // 设置session
        session.setAttribute(WebSecurityConfig.SESSION_KEY, username);

        map.put("success", true);
        map.put("message", "登录成功");
        return map;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 移除session
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:/login";
    }

}
