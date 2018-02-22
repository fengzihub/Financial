package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.business.domain.MoneyWithdraw;
import cn.fengzihub.p2p.business.mapper.MoneyWithdrawMapper;
import cn.fengzihub.p2p.business.service.IMoneyWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018.02.22.
 */
@Service
@Transactional
public class MoneyWithdrawServiceImpl implements IMoneyWithdrawService {
    @Autowired
    private MoneyWithdrawMapper moneyWithdrawMapper;
    @Override
    public int save(MoneyWithdraw moneyWithdraw) {
        moneyWithdrawMapper.insert(moneyWithdraw);
        return 0;
    }

    @Override
    public int update(MoneyWithdraw moneyWithdraw) {
        moneyWithdrawMapper.updateByPrimaryKey(moneyWithdraw);
        return 0;
    }

    @Override
    public MoneyWithdraw get(Long id) {
        return moneyWithdrawMapper.selectByPrimaryKey(id);
    }

    @Override
    public void applay(BigDecimal moneyAmount) {


        //TODO !!!!!!!!!!!!!!!!!
        //提现申请条件判断


    }
}
