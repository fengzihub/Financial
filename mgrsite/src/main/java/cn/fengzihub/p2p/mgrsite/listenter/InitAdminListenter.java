package cn.fengzihub.p2p.mgrsite.listenter;

import cn.fengzihub.p2p.base.service.ILogininfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018.02.10.
 */
@Component
public class InitAdminListenter implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ILogininfoService logininfoService;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //初始化管理员
        logininfoService.initAdmin();
    }
}
