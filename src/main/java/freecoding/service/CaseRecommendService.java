package freecoding.service;

import freecoding.entity.Case;
import freecoding.entity.Law;
import net.sf.json.JSON;
import org.dom4j.DocumentException;

import java.io.File;
import java.util.List;

/**
 * Created by zhujing on 2017/7/18.
 */
public interface CaseRecommendService {
    /**
     * 用户上传文件
     * @param file
     * @return 上传结果
     */
    boolean upload(File file);


    /**
     * 处理用户上传文书
     * @param keyword
     * @return 处理结果
     */
    JSON handle(String keyword) throws DocumentException;


    /**
     * 案例推荐
     * @return
     */
    List<Case> getCaseRecommendation();


    /**
     * 推荐案例法条分布
     * @return
     */
    List<Law> getLawDistribution();



}
