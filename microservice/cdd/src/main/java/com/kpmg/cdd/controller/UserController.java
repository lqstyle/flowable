package com.kpmg.cdd.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.kpmg.cdd.entity.*;
import com.kpmg.cdd.service.*;
import com.kpmg.cdd.util.UserUtils;
import com.kpmg.cdd.util.UuidUtils;
import com.kpmg.cdd.util.vo.Page;
import com.kpmg.cdd.util.vo.UserVo;
import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author lucasliang
 * @version 0.0.1-SNAPSHOT
 * @description: user Controller
 * @date 07/06/2018 10:43 afternoon
 */
@RestController
@RequestMapping("/v1")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RoleService roleService;


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Page getCaseList(@RequestParam(value = "page") int pageIndex,
                            @RequestParam int limit,
                            @RequestParam(required = false) String userName,
                            HttpServletRequest request) {
        User user = UserUtils.getCurrentUser(request);

        PageHelper.startPage(pageIndex, limit);
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isEmpty(userName)) {
            map.put("loginName", userName);
        }

        List<Map<String, Object>> list = userService.getUserInFoListByMap(map);

        Page page = new Page();
        PageInfo<Map<String, Object>> pageInfo;
        if (!CollectionUtils.isEmpty(list)) {
            pageInfo = new PageInfo<>(list);
            page.setTotal(pageInfo.getTotal());
        }
        page.setItems(list);
        return page;
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public User getUserInfo(@PathVariable String userId) {
        return userService.selectByPrimaryKey(userId);
    }


    /**
     * function description:validate user sign in
     *
     * @param: [session, request]
     * @return: java.util.Map
     * @author: lucasliang
     * @date: 08/06/2018 2:24 afternoon
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public void validateUser(@RequestBody UserVo userVo, HttpServletRequest request) {
        User loginUser = UserUtils.getCurrentUser(request);
        Preconditions.checkArgument(loginUser != null, "loginUser can not be null");
        User user = userVo.getUser();
        user.setId(UuidUtils.getSimpleId());
        user.setCreateDate(new Date());
        user.setCreateBy(loginUser.getId());
        user.setLoginName(user.getUsername());
        user.setDelFlag("0");
        userService.saveUser(user);
    }



    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public int updateUser(@RequestBody User user) {
        user.setLoginName(user.getUsername());
        user.setDelFlag("0");
        return userService.updateByPrimaryKey(user);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    public int updateUser(@PathVariable String userId) {
        return userService.deleteUser(userId);
    }

}
