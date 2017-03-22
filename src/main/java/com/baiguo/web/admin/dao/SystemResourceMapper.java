package com.baiguo.web.admin.dao;

import java.util.List;

import com.baiguo.web.admin.model.SystemResource;
/**
 * 
 * @description 
 * @author ldw
 * @datetime 2016年12月23日 下午3:21:52
 */
public interface SystemResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SystemResource record);

    int insertSelective(SystemResource record);

    SystemResource selectByPrimaryKey(Integer id);

    List<String> findPermissionsByUserName(String userName);
    
    List<SystemResource> findListByUserId(Integer userId);
    
    int updateByPrimaryKeySelective(SystemResource record);

    int updateByPrimaryKey(SystemResource record);
}