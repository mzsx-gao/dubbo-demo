package gao.soa.dubboprovider.validation;

import com.alibaba.dubbo.config.annotation.Service;
import gao.soa.dubbointerface.ValidationService;
import gao.soa.dubbointerface.entity.ValidationParamter;

@Service
public class ValidationServiceImpl implements ValidationService {
    
    public void save(ValidationParamter parameter) {
        System.out.println("==================save");
    }
    
    public void update(ValidationParamter parameter) {
        System.out.println("==================update");
    }
    
    public void delete(long id, String operation) {
        System.out.println("==================delete");
    }
    
}
