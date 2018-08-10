package gao.soa.dubboprovider.shiyong;

import com.alibaba.dubbo.config.annotation.Service;
import gao.soa.dubbointerface.AreaManager;

@Service
public class AreaManagerImpl implements AreaManager {
    
    public String queryArea(String id) {
        System.out.println("====================" + id);
        return "======" + id;
    }
    
}
