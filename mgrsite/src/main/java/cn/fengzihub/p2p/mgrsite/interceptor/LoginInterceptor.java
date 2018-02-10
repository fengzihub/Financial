package cn.fengzihub.p2p.mgrsite.interceptor;

import cn.fengzihub.p2p.base.util.UserContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018.02.10.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //不为空,说明有注解,需要登录才能访问
        if (UserContext.getCurrent() == null) {
            response.sendRedirect("/login.html");
            return false;
        }
        return true;
    }
}
