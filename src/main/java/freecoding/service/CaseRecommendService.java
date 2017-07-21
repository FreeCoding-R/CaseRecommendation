package freecoding.service;

import freecoding.exception.ServiceProcessException;
import freecoding.vo.Case;
import freecoding.vo.Law;
import freecoding.exception.FileContentException;
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
     * 用户选择文件传入id
     * @param id
     * @return
     * @throws DocumentException
     */
    boolean init(String id) throws DocumentException;


    /**
     * 处理用户上传文书
     * @param
     * @return 处理结果
     */
    JSON handle() throws DocumentException, FileContentException, ServiceProcessException;


    /**
     * 用户上传文书细节摘取
     * @param keyword
     * @return
     */
    JSON detail(String keyword) throws DocumentException, FileContentException, ServiceProcessException;


    /**
     * 案例推荐
     * @return
     */
    List<Case> getCaseRecommendation() throws ServiceProcessException;


    /**
     * 推荐案例法条分布
     * @return
     */
    List<Law> getLawDistribution() throws ServiceProcessException, DocumentException;



}
