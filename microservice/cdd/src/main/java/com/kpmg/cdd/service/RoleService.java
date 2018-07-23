package com.kpmg.cdd.service;

import com.kpmg.cdd.entity.Role;
import com.kpmg.cdd.repository.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lucasliang
 * @version 0.0.1-SNAPSHOT
 * @description: ${todo}
 * @date 09/06/2018 10:42 
 */
@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public List<Role> getRoleList(Map<String, Object> map) {
        return roleMapper.getRoleList(map);
    }

    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    public Role selectByPrimaryKey(String id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKey(Role record) {
        return roleMapper.updateByPrimaryKey(record);
    }
}
