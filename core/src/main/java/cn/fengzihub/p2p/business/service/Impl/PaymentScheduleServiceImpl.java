package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.business.domain.PaymentSchedule;
import cn.fengzihub.p2p.business.mapper.PaymentScheduleMapper;
import cn.fengzihub.p2p.business.service.IPaymentScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018.02.21.
 */
@Service
@Transactional
public class PaymentScheduleServiceImpl implements IPaymentScheduleService {
    @Autowired
    private PaymentScheduleMapper paymentScheduleMapper;
    @Override
    public int save(PaymentSchedule paymentSchedule) {
        paymentScheduleMapper.insert(paymentSchedule);
        return 0;
    }

    @Override
    public int update(PaymentSchedule paymentSchedule) {
        paymentScheduleMapper.updateByPrimaryKey(paymentSchedule);
        return 0;
    }

    @Override
    public PaymentSchedule get(Long id) {
        return paymentScheduleMapper.selectByPrimaryKey(id);
    }
}
