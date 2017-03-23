package com.baiguo.framework.utils;
/**
 * 状态工具类
 * @author ldw
 *
 */
public class StatusUtils {
	/**
	 * 用户状态，正常使用
	 */
	public static final Integer _USER_OK = 0;
	/**
	 * 用户状态，锁定中
	 */
	public static final Integer _USER_LOCK = 1;
	/**
	 * 用户状态，已删除
	 */
	public static final Integer _USER_DEL = 2;
	
	/*
	 * 升序 
	 */
	public static final String _ORDER_ASC = "asc";
	/*
	 * 倒序
	 */
	public static final String _ORDER_DESC = "desc";
	/*
	 * 已关注
	 */
	public static final int _WX_SUBSCRIBE = 1;
	/*
	 * 未关注
	 */
	public static final int _WX_UNSUBSCRIBE = 0;
	/*
	 * 关注事件类型，订阅
	 */
	public static final String _WX_EVENT_SUBSCRIBE = "subscribe";
	/*
	 * 关注事件类型，取消订阅
	 */
	public static final String _WX_EVENT_UNSUBSCRIBE = "unsubscribe";
	/*
	 * 测评完成状态
	 */
	public static final int _TEST_OK = 1;
	/*
	 * 测评未完成状态
	 */
	public static final int _TEST_NO_OK = 0;
	/*
	 * 发布状态，发布
	 */
	public static final String _PUBLISH_STATUS_OK = "publish";
	/*
	 * 发布状态，回收
	 */
	public static final String _PUBLISH_STATUS_NO_OK = "trash";
	
	/*
	 * 回返状态，已取消
	 */
	public static final int _BACK_STATUS_CANCEL = 0;
	/*
	 * 回返状态，已参加
	 */
	public static final int _BACK_STATUS_ATTEND = 1;
	/*
	 * 回返状态，已电话确认
	 */
	public static final int _BACK_STATUS_ENTER = 2;
	/*
	 * 回返状态，未确认
	 */
	public static final int _BACK_STATUS_UNCOMFIRMED = 3;
	
	/*
	 * 支付状态，未支付
	 */
	public static final int _CARE_PAY_NO = 0;
	/*
	 * 支付状态，已支付
	 */
	public static final int _CARE_PAY_PAID = 1;
	/*
	 * 支付状态，线下支付
	 */
	public static final int _CARE_PAY_OFFLINE = 2;
	/*
	 * 验证码在redis的存在时间
	 */
	public static final int _CHECK_TIME_OUT = 600;
	/*
	 * 任务完成状态，已完成
	 */
	public static final int _TASK_FINISH = 1;
	/*
	 * 任务完成状态，未完成
	 */
	public static final int _TASK_NO_FINISH = 0;
	/*
	 * 预约状态，预约等待
	 */
	public static final String _CONSULT_WAIT = "预约等待";
	/*
	 * 攻略分类
	 */
	public static final Integer _MESSAGE_TYPE = 1;
	/*
	 * 微信预支付统一下单接口返回成功
	 */
	public static final String _RETURN_CODE = "SUCCESS";
	/*
	 * 微信预支付统一下单接口返回成功
	 */
	public static final String _RESULT_CODE = "SUCCESS";
	/*
	 * hibernate,等于
	 */
	public static final String _HIBERNATE_EQ = "eq";
	/*
	 * hibernate,大于等于
	 */
	public static final String _HIBERNATE_GE = "ge";
	/*
	 * hibernate,大于
	 */
	public static final String _HIBERNATE_GT = "gt";
	/*
	 * hibernate,小于等于
	 */
	public static final String _HIBERNATE_LE = "le";
	/*
	 * hibernate,小于
	 */
	public static final String _HIBERNATE_LT = "lt";
	/*
	 * 当前主题时间对应在redis上的key
	 */
	public static final String _REDIS_THEME_TIME = "theme_now_date";
}
