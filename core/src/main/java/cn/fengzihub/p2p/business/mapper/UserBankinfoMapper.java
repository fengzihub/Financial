package cn.fengzihub.p2p.business.mapper;

import cn.fengzihub.p2p.business.domain.UserBankinfo;

public interface UserBankinfoMapper {
    int insert(UserBankinfo record);

    UserBankinfo selectByUserId(Long userinfoId);

}