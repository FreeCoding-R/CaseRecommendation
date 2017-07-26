package preData;

import freecoding.dao.CaseRecommendDao;
import freecoding.dao.impl.CaseRecommendDaoImpl;

import java.io.File;

public class test {
    public static void main(String args[]){
        CaseRecommendDao caseRecommend = new CaseRecommendDaoImpl();
        File file = new File("/Users/loick/Desktop/group3/train/C__Users_Administrator_Desktop_刑事二审案件_刑事二审案件_64.xml");
        System.out.println(caseRecommend.getKmeansCases(file));

//        String cp = "src/main/python2.0/query.py";
//
//        Process process = null;
//        try {
//            process = Runtime.getRuntime().exec("python3 " + cp+" "+file);
//            InputStream is = process.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            String line;
//            while((line = reader.readLine()) != null){
//                System.out.println(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
