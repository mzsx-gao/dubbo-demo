package gao.soa.dubboprovider;

import com.alibaba.dubbo.config.annotation.Service;
import gao.soa.dubbointerface.MockService;

@Service
public class MockServiceImpl implements MockService {
    
    public String mock(String param) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("====================MockServiceImpl.mock");
        return "====================MockServiceImpl.mock";
    }
    
}
