package com.kpmg.cdd.controller;

import org.flowable.ui.common.model.UserRepresentation;
import org.flowable.ui.common.security.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountController {

    /**
     *
     * just a mock
     * flowable model designer need this
     * flowable ui need
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/v1/account",method = RequestMethod.GET)
    public UserRepresentation account(){
        UserRepresentation userRepresentation=new UserRepresentation();
        userRepresentation.setId("admin");
        userRepresentation.setFirstName("admin");
        userRepresentation.setLastName("admin");
        userRepresentation.setEmail("admin@kpmg.com");
        userRepresentation.setFullName("admin");
        List<String> privileges=new ArrayList<>();
        privileges.add("access-idm");
        privileges.add("access-task");
        privileges.add("access-modeler");
        privileges.add("access-admin");
        userRepresentation.setPrivileges(privileges);
        return userRepresentation;
    }

}
