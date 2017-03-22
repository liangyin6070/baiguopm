package com.baiguo.web.admin.dao;

import com.baiguo.web.admin.model.SystemLog;
/**
 * 
 * @description 
 * @author ldw
 * @datetime 2017年3月22日 上午11:49:18
 */
public interface SystemLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemLog record);

    int insertSelective(SystemLog record);

    SystemLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SystemLog record);

    int updateByPrimaryKey(SystemLog record);
}