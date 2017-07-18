package freecoding.service;

import freecoding.entity.Law;
import net.sf.json.JSON;

import java.io.File;
import java.util.List;

/**
 * Created by zhujing on 2017/7/18.
 */
public interface CaseRecommendService {
    /**
     *
     * @param file
     * @return
     */
    boolean upload(File file);


    /**
     *
     * @param keyword
     * @return
     */
    JSON handle(String keyword);


    /**
     *
     * @return
     */
    List<JSON> getCaseRecommendation();


    /**
     *
     * @return
     */
    List<Law> getLawDistribution();

}
