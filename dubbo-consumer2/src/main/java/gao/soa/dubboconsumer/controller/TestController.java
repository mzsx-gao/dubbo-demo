package gao.soa.dubboconsumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import gao.soa.dubbointerface.Group;
import gao.soa.dubbointerface.UserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/gsd")
public class TestController implements ApplicationContextAware {
    
    ApplicationContext application;
    
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.application = applicationContext;
    }
    
    @Reference(cluster = "failover", retries = 3, loadbalance = "random")
    private UserService userServiceImpl;
    
    @RequestMapping("/queryUser")
    @ResponseBody
    public String queryUser() {
        return userServiceImpl.queryUser("123");
    }

    @Reference(group = "groupImpl1")
    private Group group1;

    @Reference(group = "groupImpl2")
    private Group group2;

    @RequestMapping("/grouptest")
    @ResponseBody
    public String grouptest() {
        String groupstr1 = group1.doSomething("xx");
        String groupstr2 = group2.doSomething("xx");
        return groupstr1 + groupstr2;
    }

    @RequestMapping("/versiontest")
    @ResponseBody
    public  String versiontest() {
        String groupstr1 = group1.doSomething("xx");
        return groupstr1;
    }
    
}
