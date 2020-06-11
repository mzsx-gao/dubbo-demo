package gao.soa.dubbo.rmi;

import gao.soa.dubbo.invoke.NodeInfo;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RmiServerStart {

    public void startRmiServer(String host, String port, String id) {
        try {
            SoaRmiImpl soaRmiImpl = new SoaRmiImpl();
            LocateRegistry.createRegistry(Integer.parseInt(port));
            // rmi://127.0.0.1:5689/gsdsoa
            Naming.bind("rmi://" + host + ":" + port + "/" + id, soaRmiImpl);
            System.out.println("server: 对象绑定成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public SoaRmi startRmiClient(NodeInfo nodeinfo, String id) {
        String host = nodeinfo.getHost();
        String port = nodeinfo.getPort();
        try {
            return (SoaRmi)Naming.lookup("rmi://" + host + ":" + port + "/" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
