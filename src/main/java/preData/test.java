package preData;

import freecoding.dao.CaseRecommendDao;
import freecoding.dao.impl.CaseRecommendDaoImpl;

import java.io.File;

public class test {
    public static void main(String args[]){
        CaseRecommendDao caseRecommend = new CaseRecommendDaoImpl();
        File file = new File("/Users/loick/Desktop/OSEngineer/天津/samples/1.xml");
        System.out.println(caseRecommend.getKmeansCases(file));
    }
}
