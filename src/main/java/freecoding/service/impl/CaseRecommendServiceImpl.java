package freecoding.service.impl;

import freecoding.dao.CaseRecommendDao;
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



    @Override
    public boolean upload(File file) {
        if(file==null){
            return false;
        }
        String fileName = file.getName();
        if(!fileName.endsWith(".xml")){
            return false;
        }
        return true;
    }

    @Override
    public JSON handle(String keyword) {
        String result="{";
        SAXReader sr = new SAXReader();
        org.dom4j.Document document = null;

        try {
            document = sr.read(file);

        } catch (DocumentException e) {
            e.printStackTrace();
        }

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
    public List<JSON> getCaseRecommendation() {
        return null;
    }

    @Override
    public List<Law> getLawDistribution() {
        return null;
    }

}
