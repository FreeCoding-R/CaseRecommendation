package freecoding.web.ctrl.routes;

import freecoding.web.data.model.Person;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
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

    @RequestMapping(value="/uploadXML", method= RequestMethod.POST)
    public String uploadXML(Model model,
                            @RequestParam(value = "file", required = true) MultipartFile file) {
        String message="";
        if (file.isEmpty()) {
            message="文件为空";
        }else {
            // 获取文件名
            String fileName = file.hashCode() + file.getOriginalFilename();
            message+=("上传的文件名为：" + fileName + "\n");
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            message+=("上传的后缀名为：" + suffixName + "\n");
            // 文件上传后的路径
            try {
                // 解决中文问题，liunx下中文路径，图片显示问题
                String filePath = System.getProperty("user.dir")+"/userFiles/";
                File dest = new File(filePath + fileName);
                // 检测是否存在目录
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                file.transferTo(dest);
                message+= "上传成功\n";
            }
            catch (Exception e) {
                e.printStackTrace();
                message+= "上传失败\n";
            }
        }
        model.addAttribute("result",message);
        return "result";
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
