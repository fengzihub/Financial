package cn.fengzihub.p2p.base.mapper;

import cn.fengzihub.p2p.base.domain.MailVerify;

public interface MailVerifyMapper {

    int insert(MailVerify record);

    int updateByPrimaryKey(MailVerify record);

    MailVerify get(String key);
}