package gao.soa.dubbo.loadBalance;


import gao.soa.dubbo.invoke.NodeInfo;

import java.util.List;

public interface LoadBalance {

    NodeInfo doSelect(List<String> registryInfo);

}
