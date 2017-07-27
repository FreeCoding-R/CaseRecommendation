package freecoding.util;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

/**
 * Created by zhujing on 2017/7/18.
 */
public class Json2XmlUtil {

    public static String jsonFromM2xml(String json){
        json=json.substring(51);
        json="{\"writ\":{"+json+"}";

        XMLSerializer serializer = new XMLSerializer();
        JSON jsonObject = JSONSerializer.toJSON(json);
        String h=serializer.write(jsonObject);
        h=h.substring(43,h.length()-6);


        return h;

    }


    public static String jsonPartOfM2xml(String json){
        json="{\"CPFXGC\":"+json+"}";
        XMLSerializer serializer = new XMLSerializer();
        JSON jsonObject = JSONSerializer.toJSON(json);
        String h=serializer.write(jsonObject);
        h=h.substring(43,h.length()-6);


        return h;

    }


}
