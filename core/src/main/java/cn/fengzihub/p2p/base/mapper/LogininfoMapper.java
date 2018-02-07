package cn.fengzihub.p2p.base.mapper;

import cn.fengzihub.p2p.base.domain.Logininfo;
import org.apache.ibatis.annotations.Param;

public interface LogininfoMapper {
    //登录用户只需要添加方法,删除...等等都不需要
    int insert(Logininfo record);


    int selectCountByUsername(String username);

    Logininfo login(@Param("username") String username, @Param("password") String password);

}