package cn.fengzihub.p2p;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Created by Administrator on 2018.02.07.
 */
@Configuration
@PropertySources({
        @PropertySource("classpath:application-core.properties"),
        @PropertySource("classpath:email.properties")
})

@MapperScan("cn.fengzihub.p2p.base.mapper")
public class CoreConfig {
}
