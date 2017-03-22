package com.baiguo.web.admin.dao;

import java.util.List;
import java.util.Map;

import com.baiguo.web.admin.model.SystemRole;
/**
 * 
 * @description 角色表DAO
 * @author ldw
 * @datetime 2016年12月23日 下午3:01:23
 */
public interface SystemRoleMapper {
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Integer id);
    /**
     * 新增角色
     * @param record
     * @return
     */
    int insert(SystemRole record);
    /**
     * 新增角色
     * @param record
     * @return
     */
    int insertSelective(SystemRole record);
    /**
     * 主键查询
     * @param id
     * @return
     */
    SystemRole selectByPrimaryKey(Integer id);
    /**
     * 根据账号查询角色值
     * @param userName
     * @return
     */
    List<String> selectByUserName(String userName);
    /**
     * 更新角色
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SystemRole record);
    /**
     * 更新角色
     * @param record
     * @return
     */
    int updateByPrimaryKey(SystemRole record);
    /**
     * 分页获取角色列表
     * @param params
     * @return
     */
    List<SystemRole> findByPage(Map<String, Object> params);
    /**
     * 获取角色列表数量
     * @param params
     * @return
     */
    long count(Map<String, Object> params);
}