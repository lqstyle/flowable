package com.kpmg.cdd.service;

import com.kpmg.cdd.entity.User;
import com.kpmg.cdd.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class UserService {


    @Autowired
    private UserMapper userMapper;


    public User selectUserByName(String username) {
        return userMapper.selectUserByName(username);
    }

    /**
     * Functional description:  Get the user list
     *
     * @param: [userCriteria]
     * @return: java.util.List<demo.bpm.bpmvacationflow.vo.User>
     * @author: lucasliang
     * @date: 08/06/2018 2:27
     */
    public List<User> getUserList(Map<String, Object> map) {
        return userMapper.getUserListByMap(map);
    }

    /**
     * Functional description: Save the user
     *
     * @param: [user]
     * @return: int
     * @author: lucasliang
     * @date: 09/06/2018 9:44
     */
    public int saveUser(User user) {
        return userMapper.insert(user);
    }

    public List<User> getUserListByMap(Map<String, Object> map) {
        return userMapper.getUserListByMap(map);
    }

    public List<Map<String, Object>> getUserInFoListByMap(Map<String, Object> map) {
        return userMapper.getUserInFoListByMap(map);
    }

    public User selectByPrimaryKey(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }
    public int deleteUser(String userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

}
