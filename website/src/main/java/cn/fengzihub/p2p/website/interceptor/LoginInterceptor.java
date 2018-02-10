package cn.fengzihub.p2p.website.interceptor;

import cn.fengzihub.p2p.base.util.UserContext;
import cn.fengzihub.p2p.website.util.RequirelLogin;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018.02.10.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求方法1
        HandlerMethod hm = (HandlerMethod) handler;
        RequirelLogin requirelLogin = hm.getMethodAnnotation(RequirelLogin.class);
        //判断方法上是否有贴注解
        if (requirelLogin != null) {
            //不为空,说明有注解,需要登录才能访问
            if (UserContext.getCurrent() == null) {
                response.sendRedirect("/login.html");
                return false;
            }
        }
        return true;
    }
}
