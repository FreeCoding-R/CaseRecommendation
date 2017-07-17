package freecoding.service;

import com.mongodb.util.JSON;
import freecoding.entity.Law;

import java.io.File;
import java.util.List;

/**
 * Created by zhujing on 2017/7/17.
 */
public interface CalculateService {


    List<JSON> getCaseRecommendation(File file);

    List<Law> getLawDistribution(File file);

}
