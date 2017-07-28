package freecoding.service.impl;

import freecoding.dao.CaseRecommendDao;
import freecoding.exception.FileContentException;
import freecoding.exception.ServiceProcessException;
import freecoding.service.CaseRecommendService;
import freecoding.util.Json2XmlUtil;
import freecoding.vo.Case;
import freecoding.vo.Law;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.bson.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhujing on 2017/7/18.
 */
@Service
public class CaseRecommendServiceImpl implements CaseRecommendService {

    @Autowired
    private CaseRecommendDao caseRecommendDao;
    private ThreadLocal <File> file = new ThreadLocal();
    private ThreadLocal <org.dom4j.Document> dom4jd = new ThreadLocal();
    private ThreadLocal <List<Document>> recommendCases = new ThreadLocal();
    private ThreadLocal <List<Case>> caseInfo = new ThreadLocal();
    private ThreadLocal <Element> resultNode= new ThreadLocal();
    private ThreadLocal <List<Law>> lawDistribution = new ThreadLocal();

    @Override
    public boolean upload(File file) {
        if(file==null || !file.exists() || !file.isFile()){
            return false;
        }
        //按后缀检测
        String fileName = file.getName();
        if(!fileName.endsWith(".xml")){
            return false;
        }
        this.file.set(file);

        //线程变量初始化
        this.dom4jd.set(null);
        this.recommendCases.set(new ArrayList<>());
        this.caseInfo.set(new ArrayList<>());
        this.lawDistribution.set(new ArrayList<>());

        return true;
    }

    @Override
    public boolean init(String id) {
        Document document=null;
        try{
            document=caseRecommendDao.find(id);
            this.dom4jd.set(DocumentHelper.parseText(Json2XmlUtil.jsonFromM2xml(document.toJson())));
            File file=new File("userFiles/example.xml");
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            String str="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+Json2XmlUtil.jsonFromM2xml(document.toJson());
            write.write(str);
            write.flush();
            write.close();
            this.file.set(file);
        }catch (Exception e){
            return false;
        }


        this.recommendCases.set(new ArrayList<>());
        this.caseInfo.set(new ArrayList<>());
        this.lawDistribution.set(new ArrayList<>());
        return true;
    }

    @Override
    public boolean initFromU(String id) throws DocumentException {
        Document document=null;
        try{
            document=caseRecommendDao.findByUser(id);
            this.dom4jd.set(DocumentHelper.parseText(Json2XmlUtil.jsonFromM2xml(document.toJson())));
            File file=new File("userFiles/example.xml");
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            String str="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+Json2XmlUtil.jsonFromM2xml(document.toJson());
            write.write(str);
            write.flush();
            write.close();
            this.file.set(file);
        }catch (Exception e){
            return false;
        }


        this.recommendCases.set(new ArrayList<>());
        this.caseInfo.set(new ArrayList<>());
        this.lawDistribution.set(new ArrayList<>());
        return true;
    }

    @Override
    public JSON handle() throws DocumentException, FileContentException, ServiceProcessException {
        //xml指定节点遍历，获取信息，转为string，再转为json
        String result="{";
        String detail="";
        org.dom4j.Document document = null;


        boolean tag=true;

        //调用顺序异常
        if(this.file.get()==null&&this.dom4jd.get()==null){
            throw new ServiceProcessException("未按顺序调用");
        }
        if(this.dom4jd.get()==null){
            SAXReader sr = new SAXReader();
            document = sr.read(this.file.get());
            dom4jd.set(document);
        }else {
            tag=false;
            document = dom4jd.get();
        }

        try {

            Element root = document.getRootElement().element("QW");
            //关键词详细第一部分获取



            //关键词详细第二部分获取
            result += "\""+"文书基本信息"+"\":{";
                    result += "\"" + root.element("WS").element("JBFY").attribute("nameCN").getText() + "\""+ ":" + "\"" + root.element("WS").element("JBFY").attribute("value").getText().replace(" ","\\r\\n") + "@" + "\",";
            result += "\"" + root.element("WS").element("WSMC").attribute("nameCN").getText() + "\"" + ":" + "\"" + root.element("WS").element("WSMC").attribute("value").getText().replace(" ","\\r\\n") + "\",";
            result += "\"" + root.element("WS").element("AH").attribute("nameCN").getText() + "\"" + ":" + "\"" + root.element("WS").element("AH").attribute("value").getText().replace(" ","\\r\\n") ;
            result += "\"},";

            String jbfy=root.element("WS").element("JBFY").attribute("value").getText();
            detail+="\""+jbfy+"\":"+detail("经办法院/"+jbfy)+",";

            //关键词详细第三部分获取


            result += "\""+"诉讼参与人"+"\":{";
            Element e = root.element("DSR");

            Iterator it = e.elementIterator("GSF");
            while (it.hasNext()) {
                Element i = (Element) it.next();

                if (i.attribute("value") == null) {
                    continue;
                }
                result += "\"" + "公诉方" + "\"" + ":" + "\"" + i.element("SSCYR").attribute("value").getText() + "@\",";
                String gsf=i.element("SSCYR").attribute("value").getText();
                detail+="\""+gsf+"\":"+detail("公诉方/"+gsf)+",";

            }

            Iterator itt = e.elementIterator("QSF");
            while (itt.hasNext()) {
                Element i = (Element) itt.next();

                if (i.attribute("value") == null) {
                    continue;
                }
                result += "\"" + "起诉方" + "\"" + ":" + "\"" + i.element("SSCYR").attribute("value").getText() + "@\",";
                String qsf=i.element("SSCYR").attribute("value").getText();
                detail+="\""+qsf+"\":"+detail("起诉方/"+qsf)+",";


            }

            Iterator ittt = e.elementIterator("YSF");
            while (ittt.hasNext()) {
                Element i = (Element) ittt.next();

                if (i.attribute("value") == null) {
                    continue;
                }
                result += "\"" + "应诉方" + "\"" + ":" + "\"" + i.element("SSCYR").attribute("value").getText() + "@\",";
                String ysf=i.element("SSCYR").attribute("value").getText();
                detail+="\""+ysf+"\":"+detail("应诉方/"+ysf)+",";

            }

            Iterator itttt = e.elementIterator("DLR");
            while (itttt.hasNext()) {
                Element i = (Element) itttt.next();

                if (i.attribute("value") == null) {
                    continue;
                }
                result += "\"" + "代理人" + "\"" + ":" + "\"" + i.element("SSCYR").attribute("value").getText() + "@\",";
                String dlr=i.element("SSCYR").attribute("value").getText();
                detail+="\""+dlr+"\":"+detail("代理人/"+dlr)+",";

            }
            result=result.substring(0,result.length()-1)+"},";


            //关键词详细第四部分获取
            result += "\"" + root.element("SSJL").attribute("nameCN").getText() + "\"" + ":" + "\"" + root.element("SSJL").attribute("value").getText() + "\",";
            String c = root.element("CPFXGC").attribute("value").getText();
            c = c.replace("裁定如下：", "作出裁定。");
            c = c.replace(" ","\\r\\n");

            result += "\"" + root.element("CPFXGC").attribute("nameCN").getText() + "\"" + ":" + "\"" + c + "\",";
            result += "\"" + root.element("PJJG").attribute("nameCN").getText() + "\"" + ":" + "\"" + root.element("PJJG").attribute("value").getText().replace(" ","\\r\\n") + "\",";
            result += "\"" + root.element("AJJBQK").attribute("nameCN").getText() + "\"" + ":" + "\"" + root.element("AJJBQK").attribute("value").getText().replace(" ","\\r\\n") + "\",";

            String yw = root.attribute("value").getText();
            yw=yw.replace(" ","\\r\\n");
            result += "\"" + "原文" + "\"" + ":" + "\"" + yw + "\",";
            result+=detail;


        }catch (NullPointerException e){
            throw new FileContentException("文件内容不符合要求");

        }

        result = result.substring(0, result.length() - 1) + "}";

        result = result.replace("\n", "\\n");
        result = result.replace("\r", "\\r");

        JSONObject jsonObject=JSONObject.fromObject(result);

        return (JSON) jsonObject;
    }


    private String detail(String keyword) throws DocumentException, FileContentException, ServiceProcessException {

        if(keyword==null){
            throw new FileContentException("关键字为空");
        }

        //同上
        if(this.dom4jd.get()==null){
            throw new ServiceProcessException("未按顺序调用");

        }

        String result="{";
        org.dom4j.Document document = null;
        document=this.dom4jd.get();

        this.resultNode.set(null);
        getNode(document.getRootElement(), keyword);
        Element root =this.resultNode.get();
        this.resultNode.set(null);

        if(root==null||!root.elementIterator().hasNext()){
            throw new FileContentException(root.attribute("value").getText());
        }

        //符合条件节点的子节点信息提取


        Iterator it = root.elementIterator();
        while (it.hasNext()) {
            Element i = (Element) it.next();
            if(i.attribute("value")==null){
                continue;
            }
            result+="\""+i.attribute("nameCN").getText()+"\""+":"+"\""+i.attribute("value").getText()+"\",";
        }

        result=result.substring(0,result.length()-1)+"}";


        return result;
    }

    @Override
    public List<Case> getCaseRecommendation() throws ServiceProcessException {

        //调用顺序异常
        if(this.file.get()==null&&this.dom4jd.get()==null){
            throw new ServiceProcessException("未按顺序调用");
        }

        List cl = this.caseInfo.get();
        List rl = this.recommendCases.get();


        rl=caseRecommendDao.getKmeansCases(this.file.get());
//        rl=caseRecommendDao.getRandomCases();
        this.recommendCases.set(rl);


        //获取推荐案例的简要信息
        for (int i=0;i<rl.size();i++){

            Document document= (Document) rl.get(i);

            Case c=new Case();
            c.setId(document.get("_id").toString());
            c.setName(document.get("name").toString());


            cl.add(c);
        }

        this.caseInfo.set(cl);
        return cl;
    }

    @Override
    public List<Law> getLawDistribution() throws ServiceProcessException, DocumentException {
        //调用顺序异常
        if(this.recommendCases.get()==null){
            throw new ServiceProcessException("未按顺序调用");
        }

        List rl=this.recommendCases.get();
        List ci=this.caseInfo.get();

        //推荐案例遍历
        for (int i = 0; i < rl.size(); i++) {

            Case c= (Case) ci.get(i);
            List<Law> cl=new ArrayList<>();


            Document document = (Document) rl.get(i);
            Document cpfxgc = (Document) ((Document) document.get("QW")).get("CPFXGC");

            String id=document.get("_id").toString();

            org.dom4j.Document cpfxgcXml = DocumentHelper.parseText(Json2XmlUtil.jsonPartOfM2xml(cpfxgc.toJson()));
            Element root = cpfxgcXml.getRootElement().element("CUS_FLFT_FZ_RY").element("CUS_FLFT_RY");


            if(root.attribute("class").getText().equals("object")){

                String str=root.attribute("value").getText();
                String name = str.substring(str.indexOf("《")+1,str.indexOf("》"));
                String detail = str.substring(str.indexOf("第"),str.indexOf("条")+1);
                Law law = new Law();
                law.setName(name);
                law.setDetail(detail);
                law.setNum(1);

                if(cl.contains(law)){

                }else {
                    cl.add(law);
                }

                distinct(law);


            }else {
                Iterator it = root.elementIterator("e");
                while (it.hasNext()) {
                    Element element = (Element) it.next();
                    String str=element.attribute("value").getText();
                    String name = str.substring(str.indexOf("《")+1,str.indexOf("》"));
                    String detail = str.substring(str.indexOf("第"),str.indexOf("条")+1);
                    Law law = new Law();
                    law.setName(name);
                    law.setDetail(detail);
                    law.setNum(1);
                    if(cl.contains(law)){

                    }else {
                        cl.add(law);
                    }
                    distinct(law);

                }

            }

            c.setList(cl);
            ci.set(i,c);
        }
        this.caseInfo.set(ci);
        return this.lawDistribution.get();
    }

    private void distinct(Law law){
        List<Law> result=this.lawDistribution.get();
        boolean tag=true;

        for(int i=0;i<result.size();i++){
            Law l=(Law) result.get(i);
            if(l.toString().equals(law.toString())){
                l.setNum(l.getNum()+1);
                result.set(i,l);
                tag=false;
            }
        }

        if(tag){
            result.add(law);
        }
        Collections.sort(result);
        this.lawDistribution.set(result);
    }

    //递归遍历xml
    private void getNode(Element node,String keyword){

        if(node.attribute("value")!=null&&node.attribute("nameCN")!=null){
            if(node.attribute("value").getText().contains(keyword.split("/")[1])&&node.attribute("nameCN").getText().equals(keyword.split("/")[0])){
                if(resultNode.get()==null||resultNode.get().attribute("value").getText().length()<node.attribute("value").getText().length()) {
                    resultNode.set(node);
                }
            }
        }

        //递归遍历当前节点所有的子节点,bool剪枝
        List<Element> listElement = node.elements();//所有一级子节点的list
        for (Element e : listElement) {//遍历所有一级子节点
            this.getNode(e, keyword);//递归
        }

    }



}

//FileContentException类已经移到freecoding.exception包里  --by zjy