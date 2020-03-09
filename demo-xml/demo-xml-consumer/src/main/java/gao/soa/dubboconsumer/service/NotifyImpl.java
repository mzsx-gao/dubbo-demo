package gao.soa.dubboconsumer.service;


import org.springframework.stereotype.Service;

@Service(value = "eventnotify")
public class NotifyImpl implements Notify {
    
    public void onreturn(String msg) {
        System.out.println("onreturn: " + msg);
    }
    
    public void onthrow(Throwable ex) {
        System.out.println("发生异常...调用方法onthrow: " + ex);
//        ex.printStackTrace();
    }
    
}
