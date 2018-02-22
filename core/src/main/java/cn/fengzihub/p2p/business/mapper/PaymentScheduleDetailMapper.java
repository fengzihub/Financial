package cn.fengzihub.p2p.business.mapper;

import cn.fengzihub.p2p.business.domain.PaymentScheduleDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface PaymentScheduleDetailMapper {

    int insert(PaymentScheduleDetail record);

    PaymentScheduleDetail selectByPrimaryKey(Long id);

    void updatePayDate(@Param("pId") Long pId, @Param("payDate") Date payDate);
}