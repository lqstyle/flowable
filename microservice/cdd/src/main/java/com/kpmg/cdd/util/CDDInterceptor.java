package com.kpmg.cdd.util;

import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.flowable.ui.common.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CDDInterceptor implements HandlerInterceptor {

    private static final Logger log=LoggerFactory.getLogger(CDDInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String urlPath=request.getServletPath();
        if(urlPath.startsWith("/app")){
            User user=new UserEntityImpl();
            user.setId("admin");
            SecurityUtils.assumeUser(user);
        }
        log.debug(">>>>>> request begin :[{}]", request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.debug("====== request return :[{}]", request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        log.debug("<<<<<< request end :[{}]", request.getRequestURI());
    }

}