package gao.soa.dubbo.cluster;


import gao.soa.dubbo.invoke.Invocation;
import gao.soa.dubbo.invoke.Invoke;

public class FailsafeCluster implements Cluster {
    
    public String invoke(Invocation invocation) throws Exception {
        Invoke invoke = invocation.getInvoke();
        try {
            return invoke.invoke(invocation);
        } catch (Exception e) {
            e.printStackTrace();
            return "忽略异常";
        }
    }
    
}
