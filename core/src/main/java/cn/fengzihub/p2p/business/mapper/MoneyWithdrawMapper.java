package cn.fengzihub.p2p.business.mapper;

import cn.fengzihub.p2p.business.domain.MoneyWithdraw;

public interface MoneyWithdrawMapper {

    int insert(MoneyWithdraw record);

    MoneyWithdraw selectByPrimaryKey(Long id);

    int updateByPrimaryKey(MoneyWithdraw record);
}