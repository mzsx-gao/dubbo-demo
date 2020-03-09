package gao.soa.dubboprovider.callback;

import gao.soa.dubbointerface.callback.CallbackListener;
import gao.soa.dubbointerface.callback.CallbackService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CallbackServiceImpl implements CallbackService {
    
    public void addListener(String key, CallbackListener listener) {
        System.out.println("处理addListener逻辑，然后开始回调。。。");
        listener.changed(getChanged(key));
    }
    
    private String getChanged(String key) {
        return "Changed: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
