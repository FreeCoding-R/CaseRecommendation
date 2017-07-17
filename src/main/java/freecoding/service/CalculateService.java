package freecoding.service;

import com.mongodb.util.JSON;
import freecoding.entity.Law;

import java.util.List;

/**
 * Created by zhujing on 2017/7/17.
 */
public interface CalculateService {


    List<JSON> getCaseRecommendation();

    List<Law> getLawDistribution();

}
