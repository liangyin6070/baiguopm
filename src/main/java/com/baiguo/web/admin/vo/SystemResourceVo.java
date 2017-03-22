package com.baiguo.web.admin.vo;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @description 后台菜单模型
 * @author ldw
 * @datetime 2017年3月22日 上午11:44:39
 */
public class SystemResourceVo {
	
    private Integer id;

    private Integer pid;

    private String name;

    private String type;

    private String url;

    private Integer sort;

    private String permission;

    private String remark;

    private Boolean publishStatus;

    private String icon;
    
    private List<SystemResourceVo> childs = new ArrayList<SystemResourceVo>();
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Boolean publishStatus) {
        this.publishStatus = publishStatus;
    }

	public List<SystemResourceVo> getChilds() {
		return childs;
	}

	public void setChilds(List<SystemResourceVo> childs) {
		this.childs = childs;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
    
    
}