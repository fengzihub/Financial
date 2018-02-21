package cn.fengzihub.p2p.business.service;

import cn.fengzihub.p2p.business.domain.PaymentSchedule;

/**
 * Created by Administrator on 2018.02.21.
 */
public interface IPaymentScheduleService {
    int save(PaymentSchedule paymentSchedule);

    int update(PaymentSchedule paymentSchedule);

    PaymentSchedule get(Long id);
}
