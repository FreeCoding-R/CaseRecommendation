package freecoding.web.ctrl.routes;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zjy on 2017/7/19.
 * http请求错误的返回控制
 */
@Controller
public class ErrorHandler404 implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value=ERROR_PATH)
    public String handleError(){
        return "error/error-404";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
