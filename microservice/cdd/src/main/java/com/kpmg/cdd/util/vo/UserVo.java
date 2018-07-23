package com.kpmg.cdd.util.vo;

import com.kpmg.cdd.entity.Client;
import com.kpmg.cdd.entity.User;

import java.io.Serializable;

/**
 * @author lucasliang
 * @version 0.0.1-SNAPSHOT
 * @description: ${todo}
 * @date 09/07/2018 10:27 PM
 */
public class UserVo implements Serializable {
    private static final long serialVersionUID = -7352168381520339533L;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}

