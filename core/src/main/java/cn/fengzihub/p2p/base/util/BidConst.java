package cn.fengzihub.p2p.base.util;

import java.math.BigDecimal;

/**
 * 系统中的常量
 * 
 * @author Administrator
 * 
 */
public class BidConst {

	/**
	 * 定义存储精度
	 */
	public static final int STORE_SCALE = 4;
	/**
	 * 定义运算精度
	 */
	public static final int CAL_SCALE = 8;
	/**
	 * 定义显示精度
	 */
	public static final int DISPLAY_SCALE = 2;

	/**
	 * 定义系统级别的0
	 */
	public static final BigDecimal ZERO = new BigDecimal("0.0000");

	/**
	 * 定义初始授信额度
	 */
	public static final BigDecimal INIT_BORROW_LIMIT = new BigDecimal(
			"5000.0000");

	//默认管理员的账号密码
	public static final String MANAGER_ACCOUNT = "admin";
	public static final String MANAGER_PASSWORD = "1111";

	//短信的有效时间 单位分钟
	public static final int MESSAGE_VALID_TIME = 5;
	//发送短信间隔的时间 秒
	public static final int MANAGER_INTERVAL_TIME = 90;
	//发送邮箱间隔时间
	public static final int EAMIL_VAILD_TIME = 5;


}
