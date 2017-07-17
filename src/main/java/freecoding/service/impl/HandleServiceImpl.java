package freecoding.service.impl;



import freecoding.service.HandleService;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Iterator;

/**
 * Created by zhujing on 2017/7/17.
 */

@Service
public class HandleServiceImpl implements HandleService {

    /**
     * xml提取关键字段，转为json
     * @param file
     * @return
     */
    @Override
    public JSON handle(File file, String keyword) {
        String result="{";
        SAXReader sr = new SAXReader();
        Document document = null;

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




}
