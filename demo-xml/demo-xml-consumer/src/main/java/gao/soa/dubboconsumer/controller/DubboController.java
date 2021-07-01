package gao.soa.dubboconsumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.service.EchoService;
import com.alibaba.dubbo.rpc.service.GenericService;
import gao.soa.dubbointerface.*;
import gao.soa.dubbointerface.callback.CallbackListener;
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

    @Autowired
    private AsyncService asyncService;

    @Reference
    private CallbackService callbackService;

    @Autowired
    private EventService eventServiceImpl;

    @Autowired
    private StubService stubService;

    @Autowired
    private MockService mockService;


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
    @RequestMapping("/echotest")
    @ResponseBody
    public String echotest() {
        EchoService echoService = (EchoService)userServiceImpl;
        String status = (String)echoService.$echo("OK");
        assert (status.equals("OK"));
        return "OK";
    }

    //异步调用
    @RequestMapping("/asynctest")
    @ResponseBody
    public String asynctest() {
        // 此调用会立即返回null
        String asynctoDo = asyncService.asynctoDo("书电");
        System.out.println("=============" + asynctoDo);
        // 拿到调用的Future引用，当结果返回后，会被通知和设置到此Future
        Future<String> future = RpcContext.getContext().getFuture();
        try {
            // 如果foo已返回，直接拿到返回值，否则线程wait住，等待foo返回后，线程会被notify唤醒
            String result = future.get();
            System.out.println("=================result : " + result);
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    /*
    参数回调:
    参数回调方式与调用本地 callback 或 listener 相同，只需要在 Spring 的配置文件中声明哪个参数是callback 类型即可。
    Dubbo 将基于长连接生成反向代理，这样就可以从服务器端调用客户端逻辑.
     */
    @RequestMapping("/callbacktest")
    @ResponseBody
    public  String callbacktest() {
        callbackService.addListener("shudian", (String arg0)->
            System.out.println("============callback result: " + arg0)
        );
        return "OK";
    }

    /*
     * 事件通知:
     * 在调用之前、调用之后、出现异常时，会触发 oninvoke/onreturn/onthrow 三个事件,可以配置当事件发生时，通知哪个类的哪个方法
     */
    @RequestMapping("/eventtest")
    @ResponseBody
    public  String eventtest() {
        String result = eventServiceImpl.eventdo("gaoshudian");
        return result;
    }
    /*
      本地存根:
        远程服务后，客户端通常只剩下接口，而实现全在服务器端，但提供方有些时候想在客户端也执行部分逻辑，比如：做 ThreadLocal 缓存，
        提前验证参数，调用失败后伪造容错数据等等，此时就需要在 API 中带上 Stub，客户端生成 Proxy 实例，会把 Proxy 通过构造函数
        传给 Stub，然后把 Stub 暴露给用户，Stub 可以决定要不要去调 Proxy
     */
    @RequestMapping("/stubtest")
    @ResponseBody
    public String stubtest() {
        System.out.println("本地存根调用测试...");
        String result = stubService.stub("gsd");
        return result;
    }

    /*
     本地伪装:
     本地伪装通常用于服务降级，比如某验权服务，当服务提供方全部挂掉后，客户端不抛出异常，而是通过 Mock 数据返回授权失败
     */
    @RequestMapping("/mocktest")
    @ResponseBody
    public  String mocktest() {
        String result = mockService.mock("gsd");
        System.out.println("result..."+result);
        return result;
    }


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.application = applicationContext;
    }
    
}