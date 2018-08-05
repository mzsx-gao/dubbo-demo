package gao.soa.dubbo.spring.configBean;

import gao.soa.dubbo.advice.InvokeInvocationHandler;
import gao.soa.dubbo.invoke.HttpInvoke;
import gao.soa.dubbo.invoke.Invoke;
import gao.soa.dubbo.invoke.NettyInvoke;
import gao.soa.dubbo.invoke.RmiInvoke;
import gao.soa.dubbo.loadBalance.LoadBalance;
import gao.soa.dubbo.loadBalance.RondomLoadBalance;
import gao.soa.dubbo.loadBalance.RoundRobinLoadBalance;
import gao.soa.dubbo.registry.BaseRegistryDelegate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Reference implements FactoryBean, ApplicationContextAware, InitializingBean {

    private String id;

    private String intf;

    private String check;

    private String protocol;

    private String loadbalance;

    private Invoke invoke;

    private ApplicationContext application;

    private static Map<String, Invoke> invokeMaps = new HashMap<>();

    private static Map<String, LoadBalance> loadBalances = new HashMap<>();

    //本地缓存注册中心中的服务列表信息
    private List<String> registryInfo = new ArrayList<>();


    static {
        invokeMaps.put("http", new HttpInvoke());
        invokeMaps.put("rmi", new RmiInvoke());
        invokeMaps.put("netty", new NettyInvoke());
        invokeMaps.put("jack", new NettyInvoke());

        loadBalances.put("random", new RondomLoadBalance());
        loadBalances.put("roundrob", new RoundRobinLoadBalance());
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public List<String> getRegistryInfo() {
        return registryInfo;
    }

    public void setRegistryInfo(List<String> registryInfo) {
        this.registryInfo = registryInfo;
    }

    public static Map<String, LoadBalance> getLoadBalances() {
        return loadBalances;
    }

    public static void setLoadBalances(Map<String, LoadBalance> loadBalances) {
        Reference.loadBalances = loadBalances;
    }

    public String getLoadbalance() {
        return loadbalance;
    }

    public void setLoadbalance(String loadbalance) {
        this.loadbalance = loadbalance;
    }



    /*
     * 返回一个对象，然后被spring容器管理
     * 这个方法要返回 intf这个接口的代理实例
     */
    public Object getObject() throws Exception {

        if (protocol != null && !"".equals(protocol)) {
            invoke = invokeMaps.get(protocol);
        } else {
            Protocol protocol = application.getBean(Protocol.class);
            if (protocol != null) {
                invoke = invokeMaps.get(protocol.getName());
            } else {
                invoke = invokeMaps.get("http");
            }
        }

        Object proxy = Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class<?>[]{Class.forName(intf)},
                new InvokeInvocationHandler(invoke, this));

        return proxy;
    }

    /*
     * 返回实例的类型
     */
    public Class getObjectType() {
        try {
            if (intf != null && !"".equals(intf)) {
                return Class.forName(intf);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 是否单例
     */
    public boolean isSingleton() {
        return true;
    }


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.application = applicationContext;
    }

    public void afterPropertiesSet() throws Exception {
        registryInfo = BaseRegistryDelegate.getRegistry(id, application);
    }

}