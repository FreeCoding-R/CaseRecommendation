package freecoding.service;



import net.sf.json.JSON;

import java.io.File;

/**
 * Created by zhujing on 2017/7/17.
 */
public interface HandleService {
    JSON handle(File file, String keyword);
}
