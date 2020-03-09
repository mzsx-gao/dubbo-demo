package gao.soa.dubboprovider.group;

import com.alibaba.dubbo.config.annotation.Service;
import gao.soa.dubbointerface.Group;

@Service(group = "groupImpl2")
public class GroupImpl2 implements Group {
    
    public String doSomething(String param) {
        System.out.println("=====================GroupImpl2.doSomething");
        return "GroupImpl2.doSomething";
    }
    
}
