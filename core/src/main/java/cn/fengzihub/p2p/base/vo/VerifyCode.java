package cn.fengzihub.p2p.base.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018.02.10.
 */
@Getter@Setter
public class VerifyCode implements Serializable {
    private String phoneNumber;
    private String verifyCode;
    private Date sendTime;

}
