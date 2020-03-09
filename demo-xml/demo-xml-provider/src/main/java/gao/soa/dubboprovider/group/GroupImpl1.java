package gao.soa.dubboprovider.group;

import com.alibaba.dubbo.config.annotation.Service;
import gao.soa.dubbointerface.Group;

@Service(group = "groupImpl1", version = "1.0.1")
public class GroupImpl1 implements Group {
    
    public String doSomething(String param) {
        System.out.println("=====================groupImpl1.doSomething");
        return "groupImpl1.doSomething";
    }
    
}
