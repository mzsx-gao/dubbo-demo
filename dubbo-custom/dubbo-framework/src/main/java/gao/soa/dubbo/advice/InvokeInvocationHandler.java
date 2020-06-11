package gao.soa.dubbo.advice;

import gao.soa.dubbo.cluster.Cluster;
import gao.soa.dubbo.invoke.Invocation;
import gao.soa.dubbo.invoke.Invoke;
import gao.soa.dubbo.spring.configBean.Reference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class InvokeInvocationHandler implements InvocationHandler {
    
    private Invoke invoke;
    
    private Reference reference;
    
    public InvokeInvocationHandler(Invoke invoke, Reference reference) {
        this.invoke = invoke;
        this.reference = reference;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("invoke到了InvokeInvocationHandler.......");
        
        //在这个invoke里面做一个远程的rpc调用。
        Invocation invocation = new Invocation();
        invocation.setIntf(reference.getIntf());
        invocation.setMethod(method);
        invocation.setObjs(args);
        invocation.setReference(reference);
        invocation.setInvoke(invoke);

        Cluster cluster = reference.getServers().get(reference.getCluster());
        return cluster.invoke(invocation);
    }
}
