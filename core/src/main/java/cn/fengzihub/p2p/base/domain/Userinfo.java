package cn.fengzihub.p2p.base.domain;

/**
 * Created by Administrator on 2018.02.10.
 */

import cn.fengzihub.p2p.base.util.BitStatesUtils;
import lombok.Getter;
import lombok.Setter;

//用户的基本信息
@Getter
@Setter
public class Userinfo extends BaseDomain{
    private int version;//版本号，用作乐观锁
    private long bitState = 0;//用户状态值
    private String realName;//用户实名值（冗余数据）
    private String idNumber;//用户身份证号（冗余数据）
    private String phoneNumber;//用户电话
    private String email;//电子邮箱
    private int score; //风控材料分数
    //实名认证Id
    private Long realAuthId;

    private SystemDictionaryItem incomeGrade;//收入
    private SystemDictionaryItem marriage;//婚姻情况
    private SystemDictionaryItem kidCount;//子女情况
    private SystemDictionaryItem educationBackground;//学历
    private SystemDictionaryItem houseCondition;//住房条件

    //判断手机有没有状态码
    public boolean getHasBindPhone() {
        return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_BIND_PHONE);
    }
    //判断邮箱状态码
    public boolean getHasBindEmail() {
        return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_BIND_EMAIL);
    }
    public boolean getIsBasicInfo() {
        return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_BASIC_INFO);
    }

    public boolean getIsRealAuth() {
        return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_REAL_AUTH);
    }

    public boolean getIsVedioAuth() {
        return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_VEDIO_AUTH);
    }



    //是否已经提现
    public boolean gethasMoneywithProcess() {
        return BitStatesUtils.hasState(this.bitState, BitStatesUtils.HAS_MONEY_WITH_PROCESS);
    }


    /**
     * 是否绑定银行卡
     * @return
     */
    public boolean getIsBindBank() {
        return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_BIND_BANK);
    }


    public boolean gethasBidRequestProcess() {
        return BitStatesUtils.hasState(this.bitState, BitStatesUtils.HAS_BIDREQUEST_PROCESS);
    }

    public void addSate(Long state) {
         this.bitState = BitStatesUtils.addState(this.bitState, state);
    }
    public void removeSate(Long state) {
         this.bitState = BitStatesUtils.removeState(this.bitState, state);
    }
}
