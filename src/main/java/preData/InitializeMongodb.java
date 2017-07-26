package preData;


import org.dom4j.DocumentException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//import com.mongodb.util.JSON;

/**
 * Created by loick on 16/07/2017.
 */
public class InitializeMongodb {

    static String path = "/Users/loick/Desktop/group3/train";

    public static void main(String[] args) throws DocumentException {
        File direc = new File(path);
        File allfiles[] = direc.listFiles();
        List<File> files = new ArrayList<>();
        for (int i = 0; i<allfiles.length; i++){
            if(allfiles[i].getName().endsWith(".xml")){
                files.add(allfiles[i]);
            }
        }

//        if(files.size() != 0){
//
//            MongoDatabase mongoDatabase = MongodbUtil.getDataBase();
//            MongoCollection<DBObject> collection = mongoDatabase.getCollection("cases",DBObject.class);
//
//
//            for(int i = 0; i < files.size(); i++) {
//                SAXReader sr = new SAXReader();
//                Document document = (Document) sr.read(files.get(i));
//
//                //by zj
//                String name = (document.getRootElement().element("QW")).element("WS").attribute("value").getText();
//
//
//                String responseTextObj = document.asXML();
//                responseTextObj = responseTextObj.replace("\r\n", "\\r\\n");
//                XMLSerializer xmlSerializer = new XMLSerializer();
//
//
//                net.sf.json.JSON jsonObj = xmlSerializer.read(responseTextObj);
//                String jsonStr = jsonObj.toString();
//                DBObject object = (DBObject) JSON.parse(jsonStr);
//                object.put("documentID", i);
//
//                //by zj
//                object.put("name",name);
//
//                collection.insertOne(object);
//            }
//        }

//        Properties props = new Properties();
//        props.put("python.home","path to the Lib folder");
//        props.put("python.console.encoding", "UTF-8"); // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
//        props.put("python.security.respectJavaAccessibility", "false"); //don't respect java accessibility, so that we can access protected members on subclasses
//        props.put("python.import.site","false");
//
//        Properties preprops = System.getProperties();
//
//        PythonInterpreter.initialize(preprops, props, new String[0]);
//
//        PythonInterpreter interpreter = new PythonInterpreter();
//        interpreter.execfile("python/kmeansprocess.py");
//        PyFunction runKmeans = (PyFunction) interpreter.get("runKmeans", PyFunction.class);
//        PyObject object = runKmeans.__call__(new PyList(files));
        String cp = "src/main/python2.0/processData.py";
        String cp2 = "src/main/python2.0/index.py";
        String pythonV = "";
        try {
            if (System.getProperty("os.name").startsWith("Mac")){
                pythonV = "python3";
            }else{
                pythonV = "python";
            }
            Process process = Runtime.getRuntime().exec(pythonV+" " + cp);
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }

            String line2 = "";
            Process process2 = Runtime.getRuntime().exec(pythonV+" "+cp2);
            InputStream is2 = process2.getInputStream();
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(is2));
            while((line2 = reader2.readLine()) != null){
                System.out.println(line2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
