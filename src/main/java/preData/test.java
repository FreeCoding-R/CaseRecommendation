package preData;

import freecoding.dao.CaseRecommendDao;
import freecoding.dao.impl.CaseRecommendDaoImpl;

import java.io.File;

public class test {
    public static void main(String args[]){
        String filestring = "/Users/loick/Desktop/group3/test/C__Users_Administrator_Desktop_刑事二审案件_刑事二审案件_151160.xml";
        File file = new File(filestring);
        CaseRecommendDao caseRecommendDao = new CaseRecommendDaoImpl();
        for(org.bson.Document document:caseRecommendDao.getKmeansCases(file)){
            System.out.println(document);
        }
        for(org.bson.Document document:caseRecommendDao.getKmeansCases(file)){
            System.out.println(document);
        }

    }
}
