package gao.soa.dubboprovider;

import com.alibaba.dubbo.config.annotation.Service;
import gao.soa.dubbointerface.StubService;

@Service
public class StubServiceImpl implements StubService {
    
    public String stub(String param) {
        System.out.println("=========StubServiceImpl.stub");
        return "========StubServiceImpl.stub";
    }
    
}
