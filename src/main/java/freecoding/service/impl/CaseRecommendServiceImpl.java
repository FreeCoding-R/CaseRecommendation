package freecoding.service.impl;

import freecoding.dao.CaseRecommendDao;
import freecoding.exception.FileContentException;
import freecoding.exception.ServiceProcessException;
import freecoding.service.CaseRecommendService;
import freecoding.util.Json2Xml;
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
import java.util.ArrayList;
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
    private ThreadLocal <List<Law>> lawDistribution = new ThreadLocal();
    private ThreadLocal <List<Case>> caseInfo = new ThreadLocal();
    private ThreadLocal <Element> resultNode= new ThreadLocal();

    @Override
    public boolean upload(File file) {
        if(file==null){
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
        this.lawDistribution.set(new ArrayList<>());
        this.caseInfo.set(new ArrayList<>());
        return true;
    }

    @Override
    public boolean init(String id) {
        Document document=null;
        try{
            document=caseRecommendDao.find(id);
            this.dom4jd.set(DocumentHelper.parseText(Json2Xml.json2xml(document.toJson())));
        }catch (Exception e){
            return false;
        }

        this.recommendCases.set(new ArrayList<>());
        this.lawDistribution.set(new ArrayList<>());
        this.caseInfo.set(new ArrayList<>());
        return true;
    }

    @Override
    public JSON handle() throws DocumentException, FileContentException, ServiceProcessException {
        //xml指定节点遍历，获取信息，转为string，再转为json
        String result="{";
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

        Element root = document.getRootElement();
        //关键词详细第一部分获取
        result+="\""+"原文"+"\""+":"+"\""+root.attribute("value").getText()+"\",";

        //关键词详细第二部分获取
        result+="\""+root.element("WS").element("JBFY").attribute("nameCN").getText()+"\""+":"+"\""+root.element("WS").element("JBFY").attribute("value").getText()+"\",";
        result+="\""+root.element("WS").element("WSMC").attribute("nameCN").getText()+"\""+":"+"\""+root.element("WS").element("WSMC").attribute("value").getText()+"\",";
        result+="\""+root.element("WS").element("AH").attribute("nameCN").getText()+"\""+":"+"\""+root.element("WS").element("AH").attribute("value").getText()+"\",";

        //关键词详细第三部分获取

        Element e=root.element("SSCYRQJ");
        if(tag==false){
            e=e.element("SSCYR");

        }

        Iterator it = e.elementIterator();
        while (it.hasNext()) {
            Element i=(Element) it.next() ;


            if(i.attribute("value")==null){
                continue;
            }
            result+="\""+"诉讼参与人"+"\""+":"+"\""+i.element("SSCYRMC").attribute("value").getText()+"\",";

        }

        //关键词详细第四部分获取
        result+="\""+root.element("SSJL").attribute("nameCN").getText()+"\""+":"+"\""+root.element("SSJL").attribute("value").getText()+"\",";
        result+="\""+root.element("CPFXGC").attribute("nameCN").getText()+"\""+":"+"\""+root.element("CPFXGC").attribute("value").getText()+"\",";
        result+="\""+root.element("PJJG").attribute("nameCN").getText()+"\""+":"+"\""+root.element("PJJG").attribute("value").getText()+"\",";
        result+="\""+root.element("WW").attribute("nameCN").getText()+"\""+":"+"\""+root.element("WW").attribute("value").getText()+"\",";

        result=result.substring(0,result.length()-1)+"}";

        if(result.length()==2){
            throw new FileContentException("文件内容不符合要求");
        }

        result = result.replace("\n", "\\n");
        result = result.replace("\r", "\\r");

        JSONObject jsonObject=JSONObject.fromObject(result);

        return (JSON) jsonObject;
    }

    @Override
    public JSON detail(String keyword) throws DocumentException, FileContentException, ServiceProcessException {

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
        JSONObject jsonObject=JSONObject.fromObject(result);

        return (JSON) jsonObject;
    }

    @Override
    public List<Case> getCaseRecommendation() {
        List cl = this.caseInfo.get();
        List rl = this.recommendCases.get();
        //去重复操作

        if(cl.size()!=0){
            return cl;
        }

        if (rl.size()==0){
            rl=caseRecommendDao.getRandomCases();
            this.recommendCases.set(rl);
        }

        //获取推荐案例的简要信息
        for (int i=0;i<rl.size();i++){

            Document document= (Document) rl.get(i);

            Case c=new Case();
            c.setId((String) document.get("_id"));
            c.setName((String) ((Document)document.get("WS")).get("@value"));
            cl.add(c);
        }

        this.caseInfo.set(cl);
        return cl;
    }


    @Override
    public List<Law> getLawDistribution() {
        List ll=this.lawDistribution.get();
        List rl=this.recommendCases.get();
        //去重复操作
        if(ll.size()!=0){
            return ll;
        }

        //推荐案例遍历
        for (int i = 0; i < rl.size(); i++) {

            Document document= (Document) rl.get(i);
            Document cpfxgc=(Document) document.get("CPFXGC");

            //CPFXGC下子节点FLFTMC遍历
            List<Document> f= (List<Document>) cpfxgc.get("FLFTMC");
            for(int k=0;k<f.size();k++) {
                Document flftmc = f.get(k);
                String name = (String) flftmc.get("@value");

                //FLFTMC下子节点TM遍历
                List<Document> l = (List<Document>) flftmc.get("TM");
                for (int j = 0; j < l.size(); j++) {
                    Law law = new Law();
                    law.setName(name);
                    law.setDetail(l.get(j).getString("@value"));
                    law.setNum(1);
                    //list去重计数
                    distinct(law);

                }
            }
        }

        return ll;
    }

    private void distinct(Law law){
        List ll=this.lawDistribution.get();
        Iterator iterator=ll.iterator();
        while (iterator.hasNext()){
            Law l= (Law) iterator.next();
            int num=l.getNum();
            if(l.getName().equals(law.getName())&&l.getDetail().equals(law.getDetail())){
                ((Law) iterator.next()).setNum(num+1);
            }else {
                ll.add(law);
            }
        }
        this.lawDistribution.set(ll);

    }


    //递归遍历xml
    private void getNode(Element node,String keyword){

        if(node.attribute("value")!=null){
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