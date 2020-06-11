package gao.soa.dubbo.cluster;


import gao.soa.dubbo.invoke.Invocation;
import gao.soa.dubbo.invoke.Invoke;

public class FailoverCluster implements Cluster {
    
    public String invoke(Invocation invocation) throws Exception {
        String retries = invocation.getReference().getRetries();
        int count = Integer.parseInt(retries);
        
        for (int i = 0; i < count; i++) {
            try {
                Invoke invoke = invocation.getInvoke();
                return invoke.invoke(invocation);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        
       throw new RuntimeException("尝试连接 "  + count + "次还失败");
    }
}
