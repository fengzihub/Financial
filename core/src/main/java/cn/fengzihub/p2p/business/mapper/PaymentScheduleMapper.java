package cn.fengzihub.p2p.business.mapper;

import cn.fengzihub.p2p.business.domain.PaymentSchedule;
import cn.fengzihub.p2p.business.query.PaymentScheduleQueryObject;

import java.util.List;

public interface PaymentScheduleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PaymentSchedule record);

    PaymentSchedule selectByPrimaryKey(Long id);


    int updateByPrimaryKey(PaymentSchedule record);

    List queryPage(PaymentScheduleQueryObject qo);

    List<PaymentSchedule> selectBidRequestId(Long bidRequestId);

    int queryForCount(PaymentScheduleQueryObject qo);

    List<PaymentSchedule> queryForList(PaymentScheduleQueryObject qo);
}