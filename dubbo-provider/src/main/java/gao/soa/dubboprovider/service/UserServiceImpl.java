package gao.soa.dubboprovider.service;

import gao.soa.dubbointerface.UserService;
import java.util.Map;

public class UserServiceImpl implements UserService {
    
    public String queryUser(String userId) {
        System.out.println("queryUser......String");
        return "username=jack,password=jack234";
    }
    
    public String queryUser(Map userId) {
        System.out.println("queryUser.....Map");
        return "username=jack,password=jack234";
    }
}
