package cn.fengzihub.p2p.business.service;

import cn.fengzihub.p2p.business.domain.ExpAccount;
import lombok.Getter;
import org.apache.commons.lang.time.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018.02.23.
 */
public interface IExpAccountService {
    int save(ExpAccount expAccount);

    int update(ExpAccount expAccount);

    ExpAccount get(Long id);


    /**
     * 发送体验金
     * @param id 体验金账户id
     * @param lastTime 有效期
     * @param expmoeny 发放金额
     * @param expmoneyType 发放类型
     */
    void grantExpMoney(Long id, LastTime lastTime, BigDecimal expmoeny, int expmoneyType);


    /**
     * 有效期
     */
    @Getter
    class LastTime{
        private int amount;
        private LastTimeUnit unit;

        public LastTime(int amount, LastTimeUnit unit) {
            super();
            this.amount = amount;
            this.unit = unit;
        }

        //获取一个到期时间
        public Date getReturnDate(Date date) {
            switch (this.unit) {
                case DAY:
                    return DateUtils.addDays(date, this.amount);
                case MONTH:
                    return DateUtils.addMonths(date, amount);
                case YEAR:
                    return DateUtils.addYears(date, amount);
                default:
                    return date;

            }
        }
    }

    /**
     * 持续时间单位
     */
    enum LastTimeUnit{
        DAY,MONTH,YEAR
    }

}
