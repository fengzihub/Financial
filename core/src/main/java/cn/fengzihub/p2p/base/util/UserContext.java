package cn.fengzihub.p2p.base.util;


import cn.fengzihub.p2p.base.domain.Logininfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018.01.18.
 */
public class UserContext {
    public static final String USER_IN_SESSION = "logininfo";
    public static final String VerifyCodeVo_IN_SESSION = "verifyCodeVo";

    private static HttpServletRequest getRequset() {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public static void setCurrent(Logininfo logininfo) {
        getRequset().getSession().setAttribute(USER_IN_SESSION,logininfo);
    }

    public static Logininfo getCurrent() {
        return (Logininfo) getRequset().getSession().getAttribute(USER_IN_SESSION);
    }

    //获取IP地址
    public static String getIp() {
        return getRequset().getRemoteAddr();
    }

    /*public static void setVerifyCodeVo(VerifyCodeVo verifyCodeVo) {
        getRequset().getSession().setAttribute(VerifyCodeVo_IN_SESSION,verifyCodeVo);
    }

    public static VerifyCodeVo getVerifyCodeVo() {
        return (VerifyCodeVo) getRequset().getSession().getAttribute(VerifyCodeVo_IN_SESSION);
    }
*/
}
