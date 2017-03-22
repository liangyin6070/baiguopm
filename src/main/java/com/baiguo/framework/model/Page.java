package com.baiguo.framework.model;

/**
 * 通用分页模型
 * @author 刘德伟
 *
 * @param <T>
 */
public class Page {
	
	private int pageNo;//第几页
	private int pageSize;//每页数据量
	private int total;//总共数据量
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPageNo() {
		if(pageNo <= 0) {
			return 1;
		}
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
