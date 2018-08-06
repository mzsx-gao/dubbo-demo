package gao.soa.dubbo.servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import gao.soa.dubbo.spring.configBean.Service;
import org.springframework.context.ApplicationContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DispatcherServlet extends HttpServlet {
    
      
    private static final long serialVersionUID = 2341676214124313L;

    /* 
     * 这里就会接受到消费端的请求
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONObject httpProcess = httpProcess(req);//转换请求参数未JSON格式
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
                PrintWriter pw = resp.getWriter();
                pw.write(result.toString());
            } else {
                PrintWriter pw = resp.getWriter();
                pw.write("==============no such method====================" + methodName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    private Method getMethod(Object bean, String methodName, JSONArray paramTypes) {

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
        gsd: for (Method method : retMethod) {
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
                } else {
                    isSameType = false;
                }
                
                if (!isSameType) {
                    continue gsd;
                }
            }
            
            if (isSameType) {
                return method;
            }
        }
        
        return null;
    }
    
    private JSONObject httpProcess(HttpServletRequest req) {
        StringBuffer sb = new StringBuffer();
        try {
            ServletInputStream in = req.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String s = "";
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }

            if (sb.toString().length() <= 0) {
                return null;
            } else {
                return JSONObject.parseObject(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
