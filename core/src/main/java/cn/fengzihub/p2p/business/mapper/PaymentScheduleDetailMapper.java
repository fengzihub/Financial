package cn.fengzihub.p2p.business.mapper;

import cn.fengzihub.p2p.business.domain.PaymentScheduleDetail;

public interface PaymentScheduleDetailMapper {

    int insert(PaymentScheduleDetail record);

    PaymentScheduleDetail selectByPrimaryKey(Long id);

}