package cn.fengzihub.p2p.business.service;

import cn.fengzihub.p2p.business.domain.PaymentScheduleDetail;

/**
 * Created by Administrator on 2018.02.21.
 */
public interface IPaymentScheduleDetailService {
    int save(PaymentScheduleDetail paymentScheduleDetail);

    PaymentScheduleDetail get(Long id);
}
