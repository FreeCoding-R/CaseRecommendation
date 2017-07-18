package freecoding.service.impl;

import freecoding.dao.CaseRecommendDao;
import freecoding.entity.Case;
import freecoding.entity.Law;
import freecoding.service.CaseRecommendService;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.bson.Document;
import org.dom4j.DocumentException;
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

    private File file;

    private List<Document> recommendCases;

    private List<Law> lawDistribution;

    private List<Case> caseInfo;

    public CaseRecommendServiceImpl() {
        recommendCases=new ArrayList<>();
        lawDistribution=new ArrayList<>();
        caseInfo=new ArrayList<>();

    }

    @Override
    public boolean upload(File file) {
        if(file==null){
            return false;
        }

        //后缀
        String fileName = file.getName();
        if(!fileName.endsWith(".xml")){
            return false;
        }
        return true;
    }

    @Override
    public JSON handle(String keyword) throws DocumentException {
        //xml指定节点遍历，获取信息，转为string，再转为json

        String result="{";
        SAXReader sr = new SAXReader();
        org.dom4j.Document document = null;
        document = sr.read(file);


        Element root = document.getRootElement().element(keyword);
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
        //去重复操作
        if(caseInfo.size()!=0){
            return caseInfo;
        }

        if (recommendCases.size()==0){
            recommendCases=caseRecommendDao.getRandomCases();
        }

        //获取推荐案例的简要信息
        for (int i=0;i<recommendCases.size();i++){

            Document document=recommendCases.get(i);

            Case c=new Case();
            c.setId((String) document.get("_id"));
            c.setName((String) ((Document)document.get("WS")).get("@value"));
            caseInfo.add(c);

        }
        return caseInfo;
    }

    @Override
    public List<Law> getLawDistribution() {
        //去重复操作
        if(lawDistribution.size()!=0){
            return this.lawDistribution;
        }

        //推荐案例遍历
        for (int i = 0; i < recommendCases.size(); i++) {

            Document document=recommendCases.get(i);
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

        return lawDistribution;
    }


    private void distinct(Law law){
        Iterator iterator=lawDistribution.iterator();
        while (iterator.hasNext()){
            Law l= (Law) iterator.next();
            int num=l.getNum();
            if(l.getName().equals(law.getName())&&l.getDetail().equals(law.getDetail())){
                ((Law) iterator.next()).setNum(num+1);
            }else {
                lawDistribution.add(law);
            }
        }

    }


}
