package freecoding.service.impl;

import freecoding.service.UploadService;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by zhujing on 2017/7/17.
 */

@Service
public class UploadServiceImpl implements UploadService {


    /**
     * 文件是否为xml
     * @param file
     * @return
     */

    @Override
    public boolean upload(File file) {
        String fileName = file.getName();
        if(!fileName.endsWith(".xml")){
            return false;
        }
        return true;
    }
}
