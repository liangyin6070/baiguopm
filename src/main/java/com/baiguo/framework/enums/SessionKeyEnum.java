package com.baiguo.framework.enums;
/**
 * 
 * @description session键名，枚举类
 * @author ldw
 * @datetime 2016年12月23日 下午3:37:03
 */
public enum SessionKeyEnum {

	key_admin(0, "admin_user", "后台管理系统用户"),
	key_front(1, "front_user", "前端用户"),
	key_token(2, "token", "验证重复提交"), 
	
	wechat_oauth2(10, "wechat_oauth2", "微信授权oauth2"),
	wechat_admin(11, "wechat_admin", "微信管理员"),
	wechat_front(12, "wechat_front", "微信用户");
	
	private int id;
	private String key;
	private String remark;
	
	SessionKeyEnum(int id, String key, String remark) {
		this.id = id;
		this.key = key;
		this.remark = remark;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
