package gao.soa.dubboconsumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.service.GenericService;
import gao.soa.dubbointerface.Group;
import gao.soa.dubbointerface.UserService;
import gao.soa.dubbointerface.ValidationService;
import gao.soa.dubbointerface.entity.ValidationParamter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.Set;

@Controller
@RequestMapping("/dubbotest")
public class DubboController implements ApplicationContextAware {

    ApplicationContext application;

    @Reference(cluster = "failover", retries = 3, loadbalance = "random")
    private UserService userServiceImpl;

    @Reference(group = "groupImpl1")
    private Group group1;

    @Reference(group = "groupImpl2")
    private Group group2;

    @Reference(check = false, validation = "true")
    private ValidationService validationService;

    @Reference(generic = true)
    private GenericService genericService;


    //注解调用
    @RequestMapping("/queryUser")
    @ResponseBody
    public String queryUser() {
        System.out.println("consumer2...");
        return userServiceImpl.queryUser("123");
    }

    //服务分组
    @RequestMapping("/grouptest")
    @ResponseBody
    public String grouptest() {
        String groupstr1 = group1.doSomething("xx");
        String groupstr2 = group2.doSomething("xx");
        return groupstr1 + groupstr2;
    }

    //服务分版本
    @RequestMapping("/versiontest")
    @ResponseBody
    public  String versiontest() {
        String groupstr1 = group1.doSomething("xx");
        return groupstr1;
    }

    //参数验证
    @RequestMapping("/validation")
    @ResponseBody
    public  String validation() {
        try {
            ValidationParamter parameter = new ValidationParamter();
            parameter.setName("gsd");
            parameter.setAge(80);
            parameter.setLoginDate(new Date(System.currentTimeMillis() - 10000000));
            parameter.setExpiryDate(new Date(System.currentTimeMillis() + 10000000));
            validationService.save(parameter);
            System.out.println("validation save ok");
            //validationService.delete(2, "1");
            return "OK";
        } catch (RpcException e) {
            e.printStackTrace();
            ConstraintViolationException ve = (ConstraintViolationException)e.getCause();
            Set<ConstraintViolation<?>> constraintViolations = ve.getConstraintViolations();
            System.out.println(constraintViolations);
        }
        return "OK";
    }

    //泛化调用
    @RequestMapping("/generictestshiyong")
    public @ResponseBody String generictestshiyong() {
        GenericService genericService = (GenericService)application.getBean("areaManager");
        Object result = genericService.$invoke("queryArea",
                new String[] {"java.lang.String"},
                new Object[] {"gsdid"});
        return result.toString();
    }

    //实现泛化调用
    @RequestMapping("/generictestshixian")
    @ResponseBody
    public  String generictest() {
        Object result = genericService.$invoke("eat", new String[] {"java.lang.String"}, new Object[] {"ewrewr"});
        return result.toString();
    }

    //回声测试




    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.application = applicationContext;
    }
    
}
