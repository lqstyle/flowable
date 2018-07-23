package com.kpmg.cdd.repository;

import com.kpmg.cdd.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(String id);

    int updateByPrimaryKey(Role record);

    List<Role> getRoleList(Map<String, Object> map);
}