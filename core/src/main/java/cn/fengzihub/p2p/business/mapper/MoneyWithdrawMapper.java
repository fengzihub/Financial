package cn.fengzihub.p2p.business.mapper;

import cn.fengzihub.p2p.business.domain.MoneyWithdraw;
import cn.fengzihub.p2p.business.query.MoneyWithdrawQuerObject;

import java.util.List;

public interface MoneyWithdrawMapper {

    int insert(MoneyWithdraw record);

    MoneyWithdraw selectByPrimaryKey(Long id);

    int updateByPrimaryKey(MoneyWithdraw record);

    int queryForCount(MoneyWithdrawQuerObject qo);

    List<MoneyWithdraw> queryForList(MoneyWithdrawQuerObject qo);
}