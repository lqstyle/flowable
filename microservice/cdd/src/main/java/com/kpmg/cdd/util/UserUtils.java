package com.kpmg.cdd.util;

import com.kpmg.cdd.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserUtils {

    public static User getCurrentUser(HttpServletRequest request) {
        User user= (User) request.getSession().getAttribute("user");
        return user;
    }
}
