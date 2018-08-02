package gao.soa.dubbo.invoke;

import com.alibaba.fastjson.JSONObject;
import gao.soa.dubbo.loadBalance.LoadBalance;
import gao.soa.dubbo.spring.configBean.Reference;
import java.util.List;


public class HttpInvoke implements Invoke {
    
    public String invoke(Invocation invoke) throws Exception {
        //这里需要得到一个服务列表去调用，那么服务列表怎么来？
        Reference reference = invoke.getReference();
        List<String> registryInfo = reference.getRegistryInfo();
        String loadbalance = reference.getLoadbalance();
        
        //在这里需要选择某一个服务去调用,那么在这里如何选择呢？就是一个负载均衡算法
        //轮询、随机、最小活跃数、权重
        LoadBalance loadbalanceClass = reference.getLoadBalances().get(loadbalance);
        NodeInfo nodeinfo = loadbalanceClass.doSelect(registryInfo);
        
        JSONObject sendParam = new JSONObject();
        sendParam.put("methodName", invoke.getMethod().getName());
        sendParam.put("serviceId", reference.getId());
        sendParam.put("methodParams", invoke.getObjs());
        sendParam.put("paramTypes", invoke.getMethod().getParameterTypes());
        
        String url = "http://" + nodeinfo.getHost() + ":" + nodeinfo.getPort() + nodeinfo.getContextpath();
        
        String result = HttpRequest.sendPost(url, sendParam.toJSONString());
        return result;
    }
    
}
