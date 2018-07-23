package com.kpmg.cdd.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.kpmg.cdd.service.RoleService;
import com.kpmg.cdd.util.UuidUtils;
import com.kpmg.cdd.entity.Role;
import com.kpmg.cdd.entity.User;
import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lucasliang
 * @version 0.0.1-SNAPSHOT
 * @description: role Controller
 * @date 09/06/2018 9:00 下午
 */
@RestController
@RequestMapping("/v1")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private IdentityService identityService;


    /**
     * function description: obtain role
     *
     * @param: [params]
     * @return: com.kpmg.bpm.common.ResponseEntity
     * @author: lucasliang
     * @date: 09/06/2018 9:42 下午
     */
    @RequestMapping(value = "/roles", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public PageInfo getUsers(@RequestBody Map<String, Object> params) {
        int page = Integer.parseInt(params.get("page") != null ? params.get("page").toString() : "");
        int rows = Integer.parseInt(params.get("rows") != null ? params.get("rows").toString() : "");
        PageHelper.startPage(page, rows);

        String roleName = params.get("roleName") != null ? params.get("roleName").toString() : "";
        Map<String, Object> map = new HashMap<>();
        map.put("roleName", roleName);
        List<Role> roleList = roleService.getRoleList(map);

        return new PageInfo<>(roleList);
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public List getRoleList() {
        return roleService.getRoleList(new HashMap<>());
    }

    /**
     * 功能描述:跳转至新增角色页面
     *
     * @param: [session]
     * @return: org.springframework.web.servlet.ModelAndView
     * @author: lucasliang
     * @date: 09/06/2018 9:57 下午
     */

    @RequestMapping(value = "/roles/rolePage", method = RequestMethod.GET)
    public ModelAndView showJspHome(HttpSession session) {
        ModelAndView model = new ModelAndView();
        User user = (User) session.getAttribute("User");
        Preconditions.checkArgument(user != null, "session timeout");
        Preconditions.checkArgument(!user.getLoginName().equals(""), "userName can not be null");
        Preconditions.checkArgument(!user.getId().equals(""), "userId can not be null");
        String loginName = user.getLoginName();
        model.setViewName("addRolePage");
        model.addObject("userName", loginName);
        return model;
    }

    @RequestMapping(value = "/roles/role", method = RequestMethod.POST)
    public String saveUser(Role role, String createUser) {
        String result;
        String roleId = UuidUtils.getSimpleId();
        role.setId(roleId);
        Preconditions.checkArgument(!createUser.equals(""), "current login user can not be null");
        role.setCreateBy(createUser);
        role.setCreateDate(new Date());
        role.setDelFlag("0");
        /*
        * save role
        * */
        roleService.saveRole(role);
        List<Group> activitiGroupList = identityService.createGroupQuery().groupId(roleId).list();
        if (activitiGroupList.size() == 1) {
            //update group
            Group group = activitiGroupList.get(0);
            group.setName(role.getName());
        } else {
            Group group = identityService.newGroup(roleId);
            group.setName(role.getName());
            identityService.saveGroup(group);
        }
        result = "success";
        return result;
    }

    /**
     * function description: update role
     *
     * @param: [roleId]
     * @return: int
     * @author: lucasliang
     * @date: 09/06/2018 10:35 afternoon
     */
    @RequestMapping(value = "/roles", method = RequestMethod.PUT)
    public int updateUser(String roleId) {
        Preconditions.checkArgument(!roleId.equals(""), "userId can not be null");
        Role role = roleService.selectByPrimaryKey(roleId);
        role.setDelFlag("1");
        return roleService.updateByPrimaryKey(role);
    }

}
