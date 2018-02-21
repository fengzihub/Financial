package cn.fengzihub.p2p.business.mapper;

import cn.fengzihub.p2p.business.domain.PaymentSchedule;
import java.util.List;

public interface PaymentScheduleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PaymentSchedule record);

    PaymentSchedule selectByPrimaryKey(Long id);

    List<PaymentSchedule> selectAll();

    int updateByPrimaryKey(PaymentSchedule record);
}