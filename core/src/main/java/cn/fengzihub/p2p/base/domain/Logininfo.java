package cn.fengzihub.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2018.02.07.
 */
@Getter
@Setter
public class Logininfo extends BaseDomain {
    public static final int STATE_NORMAL = 0;//正常状态
    public static final int STATE_LOCK = 1; //锁定状态
    public static final int USERTYPE_USER = 0; //普通用户
    public static final int USERTYPE_MANAGER = 1; //后台用户
    private String username;
    private String password;
    private int state;

    private int userType;

}
