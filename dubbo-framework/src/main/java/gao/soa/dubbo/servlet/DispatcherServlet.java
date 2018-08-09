package gao.soa.dubbo.servlet;

import gao.soa.dubbo.util.ReflectInvokeUtil;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class DispatcherServlet extends HttpServlet {
    
      
    private static final long serialVersionUID = 2341676214124313L;

    /* 
     * 这里就会接受到消费端的请求
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String param = httpProcess(req);//转换请求参数未JSON格式
        PrintWriter pw = resp.getWriter();
        try {
            String result = ReflectInvokeUtil.invokeService(param);
            pw.write(result);
        }catch (Exception e){
            pw.write("==============调用过程异常====================");
        }
    }
    
    private String httpProcess(HttpServletRequest req) {
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
                return sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
