package com.baiguo.web.admin.model;

import java.util.Date;
import com.baiguo.framework.model.Page;

/**
 * 
 * @description 后台操作日志
 * @author ldw
 * @datetime 2017年3月22日 上午11:47:19
 */
public class SystemLog extends Page{
    private Long id;
    private Integer userId;
    private String remoteAddr;
    private Date operateTime;
    private String operateType;
    private String operateBusiness;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr == null ? null : remoteAddr.trim();
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType == null ? null : operateType.trim();
    }

    public String getOperateBusiness() {
        return operateBusiness;
    }

    public void setOperateBusiness(String operateBusiness) {
        this.operateBusiness = operateBusiness == null ? null : operateBusiness.trim();
    }
}