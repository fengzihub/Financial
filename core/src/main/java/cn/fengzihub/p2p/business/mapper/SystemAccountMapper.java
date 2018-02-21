package cn.fengzihub.p2p.business.mapper;

import cn.fengzihub.p2p.business.domain.SystemAccount;

public interface SystemAccountMapper {

    int insert(SystemAccount record);

    SystemAccount selectByCurrent();

    int updateByPrimaryKey(SystemAccount record);
}