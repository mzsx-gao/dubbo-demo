package gao.soa.dubboprovider.service;

import com.alibaba.dubbo.config.annotation.Service;
import gao.soa.dubbointerface.UserService;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    
    public String queryUser(String userId) {
        System.out.println("从数据库查询数据。。。。8082");
        return "username=gsd,password=gsd";
    }
    
//    public String queryUser(Map userId) {
//        System.out.println("queryUser.....Map");
//        return "username=gsd,password=gsd234";
//    }
}
