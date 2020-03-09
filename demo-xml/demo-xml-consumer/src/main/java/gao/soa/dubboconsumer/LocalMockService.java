package gao.soa.dubboconsumer;

import gao.soa.dubbointerface.MockService;

public class LocalMockService implements MockService {
    
    public String mock(String arg0) {
        //你可以伪造容错数据，此方法只在出现RpcException时被执行
//        return "容错数据";
        System.out.println("调用mock方法");
        return null;
    }
}
