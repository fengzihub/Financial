package cn.fengzihub.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * Created by Administrator on 2018.02.12.
 */
@Getter@Setter
public class RealAuth extends BaseAuthDomain{
    public static final int SEX_MALE = 0;//性别男
    public static final int SEX_FEMALE = 1;//性别女
   /* public static final int STATE_NORMAL = 0;//待审核
    public static final int STATE_PASS = 1;//审核通过
    public static final int STATE_REJECT = 2;//审核拒接*/
    private String realName;//真实姓名
    private int sex;//性别
    private String idNumber;//身份证
    private String bornDate;//出生日期
    private String address;//身份证地址
    private String image1;//身份证正面
    private String image2;//身份证反面
 /*   private Logininfo applier;//申请人
    private int state;//审核状态
    private Logininfo auditor;//审核人呢
    private Date applyTime;//申请时间
    private Date auditTime;//审核时间
    private String remark;//备注*/

    public String getSexDisplay() {
        return this.sex == SEX_MALE? "男" : "女";
    }



    /**
     * 获取用户真实名字的隐藏字符串，只显示姓氏
     *
     * @param realName
     *            真实名字
     * @return
     */
    public String getAnonymousRealName() {
        if (StringUtils.hasLength(this.realName)) {
            int len = realName.length();
            String replace = "";
            replace += realName.charAt(0);
            for (int i = 1; i < len; i++) {
                replace += "*";
            }
            return replace;
        }
        return realName;
    }

    /**
     * 获取用户身份号码的隐藏字符串
     *
     * @param idNumber
     * @return
     */
    public String getAnonymousIdNumber() {
        if (StringUtils.hasLength(idNumber)) {
            int len = idNumber.length();
            String replace = "";
            for (int i = 0; i < len; i++) {
                if ((i > 5 && i < 10) || (i > len - 5)) {
                    replace += "*";
                } else {
                    replace += idNumber.charAt(i);
                }
            }
            return replace;
        }
        return idNumber;
    }

    /**
     * 获取用户住址的隐藏字符串
     *
     * @param currentAddress
     *            用户住址
     * @return
     */
    public String getAnonymousAddress() {
        if (StringUtils.hasLength(address) && address.length() > 4) {
            String last = address.substring(address.length() - 4,
                    address.length());
            String stars = "";
            for (int i = 0; i < address.length() - 4; i++) {
                stars += "*";
            }
            return stars + last;
        }
        return address;
    }


}
