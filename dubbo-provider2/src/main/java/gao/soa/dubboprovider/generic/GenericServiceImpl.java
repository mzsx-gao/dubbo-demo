package gao.soa.dubboprovider.generic;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.service.GenericException;
import com.alibaba.dubbo.rpc.service.GenericService;

/*
    泛接口实现方式主要用于服务器端没有API接口及模型类元的情况，参数及返回值中的所有POJO均用Map表示，
    通常用于框架集成，比如：实现一个通用的远程服务Mock框架，可通过实现GenericService接口处理所有服务请求。
 */
@Service
public class GenericServiceImpl implements GenericService {
    
    public void eat(String param) {
        System.out.println(GenericServiceImpl.class.getName() + ":" + "eat");
    }
    
    public Object $invoke(String method, String[] parameterTypes, Object[] args) throws GenericException {
        
        System.out.println(GenericServiceImpl.class.getName() + ":" + "$invoke");
        //这里可以根据传递的参数去调用相应的方法
        return GenericServiceImpl.class.getName() + ":" + "$invoke";
    }
    
}
