package gao.soa.dubbo.loadBalance;

import com.alibaba.fastjson.JSONObject;
import gao.soa.dubbo.invoke.NodeInfo;

import java.util.Collection;
import java.util.List;
import java.util.Random;

/*
  随机算法
 */
public class RondomLoadBalance implements LoadBalance {
    
    public NodeInfo doSelect(List<String> registryInfo) {
        Random random = new Random();
        int nextInt = random.nextInt(registryInfo.size());//产生一个随机数，最大为服务列表的数量
        
        String registry = registryInfo.get(nextInt);
        JSONObject parseObject = JSONObject.parseObject(registry);
        Collection<Object> values = parseObject.values();
        
        JSONObject node = new JSONObject();
        
        for(Object value : values) {
            node = JSONObject.parseObject(value.toString());
        }
        
        JSONObject protocol = node.getJSONObject("protocol");
        
        NodeInfo ni = new NodeInfo();
        ni.setHost(protocol.get("host") != null ? protocol.getString("host") : "");
        ni.setPort(protocol.get("port") != null ? protocol.getString("port") : "");
        ni.setContextpath(protocol.get("contextpath") != null ? protocol.getString("contextpath") : "");
        return ni;
    }
    
}