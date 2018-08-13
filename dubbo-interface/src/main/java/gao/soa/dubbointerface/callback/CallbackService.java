package gao.soa.dubbointerface.callback;

public interface CallbackService {

    void addListener(String key, CallbackListener listener);
}
