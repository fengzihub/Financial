package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.business.domain.PaymentScheduleDetail;
import cn.fengzihub.p2p.business.mapper.PaymentScheduleDetailMapper;
import cn.fengzihub.p2p.business.service.IPaymentScheduleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Administrator on 2018.02.21.
 */
@Service
@Transactional
public class PaymentScheduleDetailServiceImpl implements IPaymentScheduleDetailService {
    @Autowired
    private PaymentScheduleDetailMapper paymentScheduleDetailMapper;
    @Override
    public int save(PaymentScheduleDetail paymentScheduleDetail) {
        paymentScheduleDetailMapper.insert(paymentScheduleDetail);
        return 0;
    }

    @Override
    public PaymentScheduleDetail get(Long id) {
        return paymentScheduleDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updatePayDate(Long id, Date payDate) {
        paymentScheduleDetailMapper.updatePayDate(id, payDate);
    }

}
