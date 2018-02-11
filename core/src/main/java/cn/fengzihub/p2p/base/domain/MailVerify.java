package cn.fengzihub.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Administrator on 2018.01.22.
 */
@Getter
@Setter
public class MailVerify extends BaseDomain {

    private String email;
    private String uuid;
    private Long userId;
    private Date sendTime;

}
