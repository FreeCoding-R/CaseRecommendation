package freecoding.service.impl;

import freecoding.service.CalculateService;
import org.springframework.stereotype.Service;

/**
 * Created by zhujing on 2017/7/17.
 */
@Service
public class CalculateServiceImpl implements CalculateService {

    @Override
    public long count() {
        return 5604;
    }
}
