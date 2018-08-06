package gao.soa.dubboprovider.service;

import gao.soa.dubbointerface.UserService;
import java.util.Map;

public class UserServiceImpl implements UserService {
    
    public String queryUser(String userId) {
        System.out.println("从数据库查询数据。。。。8083");
        return "username=gsd,password=gsd234";
    }
    
//    public String queryUser(Map userId) {
//        System.out.println("queryUser.....Map");
//        return "username=gsd,password=gsd234";
//    }
}
