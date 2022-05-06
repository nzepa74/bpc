package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.DropdownDTO;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.PermissionDTO;
import com.spring.project.development.voler.dto.PermissionListDTO;
import com.spring.project.development.voler.service.PermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created By zepaG on 8/11/2021.
 */
@RestController
@RequestMapping("api/permission")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @RequestMapping(value = "/getRoles", method = RequestMethod.GET)
    public List<DropdownDTO> getRoles() {
        return permissionService.getRoles();
    }

    @RequestMapping(value = "/getScreens", method = RequestMethod.GET)
    public List<PermissionListDTO> getScreens(Integer roleId) {
        return permissionService.getScreens(roleId);
    }

    @RequestMapping(value = "/savePermission", method = RequestMethod.POST)
    public ResponseMessage savePermission(PermissionDTO permissionDTO, HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return permissionService.savePermission(permissionDTO, currentUser);
    }

}
