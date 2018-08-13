package gao.soa.dubboprovider.event;

import com.alibaba.dubbo.config.annotation.Service;
import gao.soa.dubbointerface.EventService;

@Service
public class EventServiceImpl implements EventService {
    
    public String eventdo(String param) {
        int i = 1 / 0;
        System.out.println("=====================" + param);
        return "=====================" + param;
    }
}
