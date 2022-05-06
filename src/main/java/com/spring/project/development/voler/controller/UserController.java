package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.SaUserDto;
import com.spring.project.development.voler.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created By zepaG on 8/21/2021.
 */
@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ResponseMessage addUser(HttpServletRequest request, SaUserDto saUserDto) throws Exception {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return userService.addUser(currentUser, saUserDto);
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ResponseMessage updateUser(HttpServletRequest request, SaUserDto saUserDto) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return userService.updateUser(currentUser, saUserDto);
    }

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public ResponseMessage getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "/getUserByUserId", method = RequestMethod.GET)
    public ResponseMessage getUserByUserId(String userId) {
        return userService.getUserByUserId(userId);
    }
}
