package gao.soa.dubbo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import gao.soa.dubbo.spring.configBean.Service;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/*

 */
public class InvokeUtil {

    public static String invokeService(String param){
        JSONObject httpProcess = JSONObject.parseObject(param);
        String serviceId = httpProcess.getString("serviceId");
        String methodName = httpProcess.getString("methodName");
        JSONArray paramTypes = httpProcess.getJSONArray("paramTypes");
        JSONArray methodParam = httpProcess.getJSONArray("methodParams");

        Object[] objs = null;
        if (methodParam != null) {
            objs = new Object[methodParam.size()];
            int i = 0;
            for (Object o : methodParam) {
                objs[i++] = o;
            }
        }

        //从spring容器中拿到serviceid对应的bean的实例吧，然后调用methodName
        ApplicationContext application = Service.getApplication();
        Object bean = application.getBean(serviceId);

        //反射调用方法
        Method method = getMethod(bean, methodName, paramTypes);
        try {
            if (method != null) {
                Object result = method.invoke(bean, objs);
                return result.toString();
            }  else {
                return "no such method";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private static Method getMethod(Object bean, String methodName, JSONArray paramTypes) {
        Method[] methods = bean.getClass().getMethods();
        List<Method> retMethod = new ArrayList<>();

        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                retMethod.add(method);
            }
        }

        if (retMethod.size() == 1) {
            return retMethod.get(0);
        }

        boolean isSameSize = false;
        boolean isSameType = false;
        jack: for (Method method : retMethod) {
            Class<?>[] types = method.getParameterTypes();

            if (types.length == paramTypes.size()) {
                isSameSize = true;
            }
            if (!isSameSize) {
                continue;
            }

            for (int i = 0; i < types.length; i++) {
                if (types[i].toString().contains(paramTypes.getString(i))) {
                    isSameType = true;
                }
                else {
                    isSameType = false;
                }

                if (!isSameType) {
                    continue jack;
                }
            }

            if (isSameType) {
                return method;
            }
        }

        return null;
    }
}