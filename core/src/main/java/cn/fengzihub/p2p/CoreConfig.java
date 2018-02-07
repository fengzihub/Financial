package cn.fengzihub.p2p;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Administrator on 2018.02.07.
 */
@Configuration
@PropertySource("classpath:application-core.properties")
@MapperScan("cn.fengzihub.p2p.base.mapper")
public class CoreConfig {
}
