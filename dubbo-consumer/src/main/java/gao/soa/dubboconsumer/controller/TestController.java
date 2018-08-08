package gao.soa.dubboconsumer.controller;

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
    
    @Autowired
    private UserService userServiceImpl;
    
    @RequestMapping("/queryUser")
    public @ResponseBody
    String queryUser() {
//        UserService userService = (UserService) this.application.getBean(UserService.class);
        return userServiceImpl.queryUser("123");
    }
    
}
