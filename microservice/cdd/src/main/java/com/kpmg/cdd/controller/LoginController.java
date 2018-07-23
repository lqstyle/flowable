package com.kpmg.cdd.controller;

import com.google.common.base.Preconditions;
import com.kpmg.cdd.util.vo.AuthenticatedUser;
import com.kpmg.cdd.entity.User;
import com.kpmg.cdd.service.UserService;
import com.kpmg.cdd.util.AuthenticationFilter;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;


@RestController
@RequestMapping(value = "/v1/login")
@Api(description = "Log in")
public class LoginController {


    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        Preconditions.checkArgument(!StringUtils.isEmpty(user.getLoginName()), "username can not be null");
        Preconditions.checkArgument(!StringUtils.isEmpty(user.getPassword()), "password can not be null");
        User realUser = userService.selectUserByName(user.getLoginName());
        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        if (realUser.getPassword().equals(user.getPassword())) {
            realUser.setPassword("");
            request.getSession().setAttribute("user", realUser);
            authenticatedUser.setId(realUser.getId());
            authenticatedUser.setName(realUser.getLoginName());
            authenticatedUser.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            authenticatedUser.setIntroduction("I'm" + realUser.getLoginName());
            if(realUser.getLoginName().equals("admin")){
                authenticatedUser.setRoles(new ArrayList<String>() {{
                    add("admin");
                }});
            }else{
                authenticatedUser.setRoles(new ArrayList<String>() {{
                    add("normal");
                }});
            }
            authenticatedUser.setToken(request.getSession().getId());
            AuthenticationFilter.AUTHTOKENS.put(request.getSession().getId(), realUser);
            return authenticatedUser;
        } else {
            response.setStatus(401);
            return new HashMap<String, String>() {{
                put("code", "error");
            }};
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(@RequestHeader String token, HttpServletRequest request) {
        request.getSession().invalidate();
        AuthenticationFilter.AUTHTOKENS.remove(token);
        return;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public AuthenticatedUser getCurrentUserInfo(@RequestParam String token, HttpServletRequest request) {
        User user = AuthenticationFilter.AUTHTOKENS.get(token);
        if(user==null){
            return null;
        }
        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        authenticatedUser.setId(user.getId());
        authenticatedUser.setName(user.getLoginName());
        authenticatedUser.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        authenticatedUser.setIntroduction("I'm" + user.getLoginName());
        if(user.getLoginName().equals("admin")){
            authenticatedUser.setRoles(new ArrayList<String>() {{
                add("admin");
            }});
        }else{
            authenticatedUser.setRoles(new ArrayList<String>() {{
                add("normal");
            }});
        }
        authenticatedUser.setToken(token);
        return authenticatedUser;
    }

}
