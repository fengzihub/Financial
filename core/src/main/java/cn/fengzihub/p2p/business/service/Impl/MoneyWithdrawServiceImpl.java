package cn.fengzihub.p2p.business.service.Impl;

import cn.fengzihub.p2p.base.domain.Account;
import cn.fengzihub.p2p.base.domain.Userinfo;
import cn.fengzihub.p2p.base.service.IAccountService;
import cn.fengzihub.p2p.base.service.IUserinfoService;
import cn.fengzihub.p2p.base.util.BidConst;
import cn.fengzihub.p2p.base.util.BitStatesUtils;
import cn.fengzihub.p2p.base.util.PageResult;
import cn.fengzihub.p2p.base.util.UserContext;
import cn.fengzihub.p2p.business.domain.MoneyWithdraw;
import cn.fengzihub.p2p.business.domain.SystemAccount;
import cn.fengzihub.p2p.business.domain.UserBankinfo;
import cn.fengzihub.p2p.business.mapper.MoneyWithdrawMapper;
import cn.fengzihub.p2p.business.query.MoneyWithdrawQuerObject;
import cn.fengzihub.p2p.business.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018.02.22.
 */
@Service
@Transactional
public class MoneyWithdrawServiceImpl implements IMoneyWithdrawService {
    @Autowired
    private MoneyWithdrawMapper moneyWithdrawMapper;
    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserBankinfoService userBankinfoService;
    @Autowired
    private IAccountFlowService accountFlowService;
    @Autowired
    private ISystemAccountService systemAccountService;
    @Autowired
    private ISystemAccountFlowService systemAccountFlowService;
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


    //提现申请
    @Override
    public void applay(BigDecimal moneyAmount) {

        //提现申请条件判断
        Userinfo userinfo = userinfoService.getCurrent();
        Account account = accountService.getCurrent();
        if (userinfo.getIsBindBank() && !userinfo.gethasMoneywithProcess() &&//判断当前用户没有一个提现申请,已经绑定银行卡
                moneyAmount.compareTo(account.getUsableAmount()) <= 0 &&//判断提现金额<=用户可用余额
                moneyAmount.compareTo(BidConst.MIN_WITHDRAW_AMOUNT)>=0 //提现金额>=系统最小提现金额
                ) {

            UserBankinfo ubk = userBankinfoService.getByUserinfoId(userinfo.getId());
            //创建申请提现对象
            MoneyWithdraw mw = new MoneyWithdraw();
            mw.setForkName(ubk.getForkName());
            mw.setBankName(ubk.getBankName());
            mw.setApplyTime(new Date());
            mw.setAccountNumber(ubk.getAccountNumber());
            mw.setAmount(moneyAmount);
            mw.setAccountName(ubk.getAccountName());
            mw.setChargeFee(BidConst.MONEY_WITHDRAW_CHARGEFEE);
            mw.setApplier(UserContext.getCurrent());
            mw.setState(MoneyWithdraw.STATE_NORMAL);
            this.save(mw);

            //对于账户:冻结金额增加,可用金额减少
            account.setUsableAmount(account.getUsableAmount().subtract(moneyAmount));
            account.setFreezedAmount(account.getFreezedAmount().add(moneyAmount));

            accountFlowService.createMoneyWith(account, mw.getChargeFee());
            accountService.update(account);

            //用户添加状态码
            userinfo.addSate(BitStatesUtils.HAS_MONEY_WITH_PROCESS);//提现申请状态码
            userinfoService.update(userinfo);
        }


    }

    @Override
    public PageResult queryPage(MoneyWithdrawQuerObject qo) {
        int count = moneyWithdrawMapper.queryForCount(qo);
        List<MoneyWithdraw> list = this.moneyWithdrawMapper.queryForList(qo);
        return new PageResult(count, list);
    }

    //提现审核
    @Override
    public void audit(Long id, int state, String remark) {
        //根据id获取提现申请单
        MoneyWithdraw m = moneyWithdrawMapper.selectByPrimaryKey(id);
        //判断 提现单 待审核状态
        if (m != null && m.getState() == MoneyWithdraw.STATE_NORMAL) {
            //设置审核时间,审核人,审核备注
            m.setAuditor(UserContext.getCurrent());
            m.setAuditTime(new Date());
            m.setRemark(remark);
            m.setState(state);

            Account account = accountService.get(m.getApplier().getId());
            if (state == MoneyWithdraw.STATE_PASS) {
                //审核通过
                //支付提现手续费
                account.setFreezedAmount(account.getFreezedAmount().subtract(m.getChargeFee()));
                //支付手续费流水
                BigDecimal chargeFee = m.getChargeFee();
                accountFlowService.createMoneyWithChargeFee(account, chargeFee);
                SystemAccount systemAccount = systemAccountService.getCurrent();
                systemAccount.setUsableAmount(systemAccount.getUsableAmount().add(chargeFee));
                systemAccountService.update(systemAccount);
                systemAccountFlowService.createGetChargeFee(systemAccount, chargeFee);

                //冻结金额减少
                BigDecimal realWithdrawFee = m.getAmount().subtract(chargeFee);
                account.setFreezedAmount(account.getFreezedAmount().subtract(realWithdrawFee));
                accountFlowService.createMoneyWithSuccess(account, realWithdrawFee);

            } else {
                //审核拒绝
                //提现账户冻结金额减少
                account.setFreezedAmount(account.getFreezedAmount().subtract(m.getAmount()));
                //提现账户可用余额增加
                account.setUsableAmount(account.getUsableAmount().add(m.getAmount()));
                accountFlowService.createMoneyWithFailed(account, m.getAmount());

            }
            accountService.update(account);
            this.update(m);

            //取消用户状态码
            Userinfo userinfo = userinfoService.get(m.getApplier().getId());
            userinfo.removeSate(BitStatesUtils.HAS_MONEY_WITH_PROCESS);
            userinfoService.update(userinfo);

        }



    }
}
