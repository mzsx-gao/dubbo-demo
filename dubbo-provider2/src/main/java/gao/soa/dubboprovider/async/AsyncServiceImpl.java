package gao.soa.dubboprovider.async;

import com.alibaba.dubbo.config.annotation.Service;
import gao.soa.dubbointerface.AsyncService;

@Service(timeout = 3000)
public class AsyncServiceImpl implements AsyncService {
    
    public String asynctoDo(String name) {
        for (int i = 0; i < 10; i++) {
            System.out.println("===============AsyncServiceImpl.asynctoDo");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "hello," + name;
    }
}
