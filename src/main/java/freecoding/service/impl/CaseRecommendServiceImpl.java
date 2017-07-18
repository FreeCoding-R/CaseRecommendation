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

    private ThreadLocal <File> file = new ThreadLocal();
    private ThreadLocal <List<Document>> recommendCases = new ThreadLocal();
    private ThreadLocal <List<Law>> lawDistribution = new ThreadLocal();
    private ThreadLocal <List<Case>> caseInfo = new ThreadLocal();

    @Override
    public boolean upload(File file) {
        //后缀
        String fileName = file.getName();
        if(!fileName.endsWith(".xml")){
            return false;
        }
        this.file.set(file);
        this.recommendCases.set(new ArrayList<>());
        this.lawDistribution.set(new ArrayList<>());
        this.caseInfo.set(new ArrayList<>());
        return true;
    }

    @Override
    public boolean init(String id) {

//        this.file.set(file);
        this.recommendCases.set(new ArrayList<>());
        this.lawDistribution.set(new ArrayList<>());
        this.caseInfo.set(new ArrayList<>());
        return true;
    }

    @Override
    public JSON handle(String keyword) throws DocumentException {
        //xml指定节点遍历，获取信息，转为string，再转为json

        String result="{";
        SAXReader sr = new SAXReader();
        org.dom4j.Document document = null;
        document = sr.read(this.file.get());


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


}
