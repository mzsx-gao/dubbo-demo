package gao.soa.dubboconsumer;

import gao.soa.dubbointerface.StubService;

public class LocalStubProxy implements StubService {
    
    private StubService stubService;
    
    public LocalStubProxy(StubService stubService) {
        this.stubService = stubService;
    }
    
    public String stub(String arg0) {
        try {
            System.out.println("此代码在客户端执行, 你可以在客户端做ThreadLocal本地缓存，或预先验证参数是否合法，等等");
            return stubService.stub("gsd");
        } catch (Exception e) {
            //你可以容错，可以做任何AOP拦截事项
            System.out.println("容错数据");
        }
        return null;
    }
}
