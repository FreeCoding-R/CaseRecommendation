package freecoding.web.data.wrapper;

import freecoding.constranst.ErrorCode;
import org.springframework.stereotype.Service;
import freecoding.web.data.response.Response;
import freecoding.web.data.vo.SampleVO;

/**
 * Created by 铠联 on 2017/5/11.
 */
@Service
public class Wrapper<T> {
    public String wrap(T vo) {
        if (vo != null) {
            return new Response<T>().getBuilder()
                    .succ()
                    .data(vo)
                    .build();
        } else {
            return new Response<SampleVO>().getBuilder()
                    .failBuild(ErrorCode.NOT_FOUND);
        }
    }
}
