package com.baiguo.framework.enums;
/**
 * 
 * @description 日志业务类型说明枚举类
 * @author ldw
 * @datetime 2017年1月23日 下午2:05:30
 */
public enum OperateTypeEnum {

	LOGIN(1, "LOGIN", "登录"),
	LOGOUT(2, "LOGOUT", "退出"),
	CREATE(3, "CREATE", "新增"),
	DELETE(4, "DELETE", "删除"),
	UPDATE(5, "UPDATE", "编辑");
	
	private int id;
	private String code;
	private String remark;
	
	private OperateTypeEnum(int id, String code, String remark) {
		this.id = id;
		this.code = code;
		this.remark = remark;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
