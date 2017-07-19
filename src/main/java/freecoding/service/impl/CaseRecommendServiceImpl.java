package freecoding.service.impl;

import freecoding.dao.CaseRecommendDao;
import freecoding.exception.FileContentException;
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
    public JSON handle() throws DocumentException, FileContentException {
        //xml指定节点遍历，获取信息，转为string，再转为json
        String result="{";
        org.dom4j.Document document = null;
        if(dom4jd.get()==null){
            SAXReader sr = new SAXReader();
            document = sr.read(this.file.get());
            dom4jd.set(document);

        }else {
            document = dom4jd.get();
        }

        Element root = document.getRootElement();
        result+="\""+"原文"+"\""+":"+"\""+root.attribute("value").getText()+"\",";

        result+="\""+root.element("WS").element("JBFY").attribute("nameCN").getText()+"\""+":"+"\""+root.element("WS").element("JBFY").attribute("value").getText()+"\",";
        result+="\""+root.element("WS").element("WSMC").attribute("nameCN").getText()+"\""+":"+"\""+root.element("WS").element("WSMC").attribute("value").getText()+"\",";
        result+="\""+root.element("WS").element("AH").attribute("nameCN").getText()+"\""+":"+"\""+root.element("WS").element("AH").attribute("value").getText()+"\",";

        Iterator it = root.element("SSCYRQJ").elementIterator();
        while (it.hasNext()) {
            Element i = (Element) it.next();
            if(i.attribute("value")==null){
                continue;
            }
            result+="\""+"诉讼参与人"+"\""+":"+"\""+i.element("SSCYRMC").attribute("value").getText()+"\",";
        }

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
    public JSON detail(String keywoed) throws DocumentException, FileContentException {
        String result="{";
        SAXReader sr = new SAXReader();
        org.dom4j.Document document = null;
        document = sr.read(this.file.get());
        Element root = getNode(document.getRootElement(),keywoed);

        if(root==null||!root.elementIterator().hasNext()){
            throw new FileContentException("未找到该关键字具体信息");
        }

        Iterator it = root.elementIterator();
        while (it.hasNext()) {
            Element i = (Element) it.next();
            if(i.attribute("value")==null){
                continue;
            }
            result+="\""+i.attribute("nameCN").getText()+"\""+":"+"\""+i.attribute("value").getText()+"\",";
        }

        result=result.substring(0,result.length()-1)+"}";

        result = result.replace("\n", "\\n");
        result = result.replace("\r", "\\r");

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
    private Element getNode(Element node,String keyword){

        Element result = null;
        if(node.attribute("value")!=null){
            if(node.attribute("value").toString().contains(keyword.split("/")[1])&&node.attribute("nameCN").toString().equals(keyword.split("/")[0])){
                result=node;
            }
        }
        //递归遍历当前节点所有的子节点
        if(result==null) {
            List<Element> listElement = node.elements();//所有一级子节点的list
            for (Element e : listElement) {//遍历所有一级子节点
                this.getNode(e, keyword);//递归
            }
        }
        return result;
    }



}

//FileContentException类已经移到freecoding.exception包里  --by zjy