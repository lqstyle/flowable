package com.kpmg.cdd.repository;

import java.util.List;
import java.util.Map;

import com.kpmg.cdd.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<Map<String, Object>> getUserInFoListByMap(Map<String, Object> map);

    List<User> getUserListByMap(Map<String, Object> map);

    User selectUserByName(String username);
}