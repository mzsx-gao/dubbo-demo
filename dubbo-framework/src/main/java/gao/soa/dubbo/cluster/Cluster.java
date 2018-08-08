package gao.soa.dubbo.cluster;


import gao.soa.dubbo.invoke.Invocation;

public interface Cluster {

    String invoke(Invocation invocation) throws Exception;

}
