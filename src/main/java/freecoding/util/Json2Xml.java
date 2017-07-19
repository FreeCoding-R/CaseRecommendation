package freecoding.util;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

/**
 * Created by zhujing on 2017/7/18.
 */
public class Json2Xml {

    public static String json2xml(String json){

//        Document document=caseRecommend.find("596b2195ec393fc6ed998155");
//        String a=document.toJson();

        json = json.replace("\n", "\\n");
        json = json.replace("\r", "\\r");
        json=json.substring(51);
        json="{\"QW\":{"+json+"}";

        XMLSerializer serializer = new XMLSerializer();
        JSON jsonObject = JSONSerializer.toJSON(json);
        String h=serializer.write(jsonObject);
        h=h.substring(43,h.length()-6);

        return h;

//        org.dom4j.Document d= DocumentHelper.parseText(h);

    }
}
