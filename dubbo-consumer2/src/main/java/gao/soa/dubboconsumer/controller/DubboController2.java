package gao.soa.dubboconsumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.service.EchoService;
import com.alibaba.dubbo.rpc.service.GenericService;
import gao.soa.dubbointerface.*;
import gao.soa.dubbointerface.callback.CallbackService;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Controller
@RequestMapping("/dubbotest2")
public class DubboController2{

    ApplicationContext application;

    @Autowired
    private UserService userServiceImpl;


    //注解调用
    @RequestMapping("/queryUser")
    @ResponseBody
    public String queryUser() {
        System.out.println("consumer2...");
        return userServiceImpl.queryUser("123");
    }

}
