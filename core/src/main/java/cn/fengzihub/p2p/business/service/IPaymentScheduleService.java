package cn.fengzihub.p2p.business.service;

import cn.fengzihub.p2p.business.domain.PaymentSchedule;
import cn.fengzihub.p2p.business.query.PaymentScheduleQueryObject;
import com.github.pagehelper.PageInfo;

/**
 * Created by Administrator on 2018.02.21.
 */
public interface IPaymentScheduleService {
    int save(PaymentSchedule paymentSchedule);

    int update(PaymentSchedule paymentSchedule);

    PaymentSchedule get(Long id);

    PageInfo queryPage(PaymentScheduleQueryObject qo);

    /**
     * 还款
     * @param id
     */
    void returnMoney(Long id);
}
