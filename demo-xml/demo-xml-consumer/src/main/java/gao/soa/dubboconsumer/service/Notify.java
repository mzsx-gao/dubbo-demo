package gao.soa.dubboconsumer.service;

public interface Notify {

    void onreturn(String msg);
    
    void onthrow(Throwable ex);
}
