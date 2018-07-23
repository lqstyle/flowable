package com.kpmg.cdd.util;

import com.kpmg.cdd.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthenticationFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    public static Map<String,User> AUTHTOKENS=new ConcurrentHashMap<>();

    public void destroy() {
        // TODO Auto-generated method stub

    }
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)req;
        HttpServletResponse response=(HttpServletResponse)res;
        String url = request.getRequestURI();
        if((url.startsWith("/v1/login/"))
                || request.getMethod().equals("OPTIONS")
                || url.indexOf("/thumbnail")>0
                || url.startsWith("/v1/file/")
                || url.startsWith("/process-api")
                || url.startsWith("/cmmn-api")
                || url.startsWith("/dmn-api")
                || url.startsWith("/app-api")
                || url.startsWith("/idm-api")
                || url.startsWith("/content-api")
                || url.startsWith("/form-api")) {
            chain.doFilter(req, res);
            request.getSession().invalidate();
            return;
        }else{
            String token=request.getHeader("token");
            if(StringUtils.isEmpty(token)){
                token=request.getParameter("token");
                if(StringUtils.isEmpty(token)){
                    this.makeError(request,response);
                    return;
                }
            }
            User user=AUTHTOKENS.get(token);
            if(user==null){
                this.makeError(request,response);
                return;
            }else{
                request.getSession().setAttribute("user",user);
                chain.doFilter(req, res);
                request.getSession().invalidate();
            }
        }
    }

    private void makeError(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setStatus(401);
        String requestType = request.getHeader("X-Requested-With");
        if("XMLHttpRequest".equals(requestType)){
            PrintWriter writer= response.getWriter();
            writer.print("{\"code\":403,\"message\":\"You need log in\"}");
            return;
        }
        request.getSession().invalidate();
    }

    public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("start AuthenticationFilter：");
    }
}