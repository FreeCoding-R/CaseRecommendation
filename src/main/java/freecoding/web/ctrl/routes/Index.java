package freecoding.web.ctrl.routes;

import freecoding.exception.FileContentException;
import freecoding.exception.ServiceProcessException;
import freecoding.service.CaseRecommendService;
import freecoding.web.data.model.Person;
import net.sf.json.JSONObject;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjy on 2017/7/15.
 */
@EnableAutoConfiguration
@Controller
public class Index {
    @Autowired
    private CaseRecommendService caseRecommendService;

    /**
     * 首页
     * @return
     */
    @RequestMapping(value= {"/","/index"},method= RequestMethod.GET)
    public String home() {
        return "index";
    }

//    @RequestMapping(value="/case", method= RequestMethod.GET)
//    public String toCase() {
//        return "case";
//    }

    /**
     * 上传文书的路由
     * @param model
     * @param file
     * @return
     */
    @RequestMapping(value="/showCase", method= RequestMethod.POST)
    public String uploadXML(Model model,
                            @RequestParam(value = "file", required = true) MultipartFile file) {
        String message="";
        boolean sucess=false;
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
            String filePath = System.getProperty("user.dir")+"/userFiles/";
            File dest = new File(filePath + fileName);
            try {
                // 解决中文问题，liunx下中文路径，图片显示问题
                // 检测是否存在目录
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                file.transferTo(dest);
                message+= "上传成功\n";

                //开始处理文书
                if(caseRecommendService.upload(dest)){
                    handleFile(model);
                    sucess=true;
                }else {
                    message+= "文件格式不符合规范！\n";
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (ServiceProcessException e) {
                e.printStackTrace();
            } catch (FileContentException e) {
                message+= (e.getMessage()+"\n");
            } catch (DocumentException e) {
                message+= (e.getMessage()+"\n");
            } catch (Exception e){
                message+= "处理文件失败\n";
                e.printStackTrace();
            }
            finally {
                if (dest.exists() && dest.isFile()) {
                    dest.delete();
                }
            }
        }
        model.addAttribute("result",message);
        model.addAttribute("sucess",sucess);
        return "case";
    }

    /**
     * 通过文书ID获取文书信息的路由
     * @param model
     * @param ID
     * @return
     */
    @RequestMapping(value="/case/{ID}",method=RequestMethod.GET)
    public String addUser4(Model model,@PathVariable String ID) {
        try {
            if(caseRecommendService.init(ID)){
                handleFile(model);
            }else {
                //如果没有这个ID的文书返回404页面
                return "error/error-404";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "case";
    }

    /**
     * 一个路由demo
     * @param model
     * @return
     */
    @RequestMapping("/person")
    public String person(Model model){
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

    /**
     * 得到目标文书后处理文书的代码复用
     * @param model 传给thymeleaf模板渲染的数据模型
     * @throws DocumentException XML格式不正确
     * @throws ServiceProcessException 处理过程异常，会在方法调用顺序不正确时抛出
     * @throws FileContentException  XML的内容不是文书的标准格式
     */
    private void handleFile(Model model) throws DocumentException, ServiceProcessException, FileContentException {
        model.addAttribute("content",(JSONObject)caseRecommendService.handle());
        model.addAttribute("caseRecommendation",caseRecommendService.getCaseRecommendation());
        model.addAttribute("lawDistribution",caseRecommendService.getLawDistribution());
    }

    @RequestMapping("/chart")
    public  String chart(Model model){
        return "chart";
    }
}
