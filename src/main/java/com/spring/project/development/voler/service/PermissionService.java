package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.voler.dao.PermissionDao;
import com.spring.project.development.voler.dto.PermissionDTO;
import com.spring.project.development.voler.dto.PermissionListDTO;
import com.spring.project.development.voler.entity.sa.Permission;
import com.spring.project.development.voler.repository.sa.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created By zepaG on 8/12/2021.
 */
@Service
public class PermissionService {
    private final PermissionDao permissionDao;
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionDao permissionDao, PermissionRepository permissionRepository) {
        this.permissionDao = permissionDao;
        this.permissionRepository = permissionRepository;
    }

    public List<DropdownDTO> getRoles() {
        return permissionDao.getRoles();
    }

    public List<PermissionListDTO> getScreens(Integer roleId) {
        List<PermissionListDTO> permissionListDTOS;
        boolean isRoleMapped = permissionDao.getIsRoleMapped(roleId);
        if (isRoleMapped) {
            permissionListDTOS = permissionDao.getRoleMappedScreens(roleId);
        } else {
            permissionListDTOS = permissionDao.getRoleUnmappedScreens();
        }
        return permissionListDTOS;
    }

    public ResponseMessage savePermission(PermissionDTO permissionDTO, CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();
        Permission permission = new Permission();
        String permissionId;

        for (PermissionListDTO permissionListDTO : permissionDTO.getPermissionListDTOS()) {
            permissionId = UuidGenerator.generateUuid();
            permission.setScreenId(permissionListDTO.getScreenId());
            permission.setRoleId(permissionDTO.getRoleId());
            permission.setViewAllowed(permissionListDTO.getViewAllowed());
            permission.setSaveAllowed(permissionListDTO.getSaveAllowed());
            permission.setEditAllowed(permissionListDTO.getEditAllowed());
            permission.setDeleteAllowed(permissionListDTO.getDeleteAllowed());
            permission.setCreatedBy(currentUser.getUserId());
            permission.setCreatedDate(new Date());
            if (!permissionListDTO.getPermissionId().equals("")) {
                permission.setPermissionId(permissionListDTO.getPermissionId());
                permissionRepository.save(permission);
            } else {
                permission.setPermissionId(permissionId);
                permissionRepository.save(permission);
            }
        }
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Saved successfully.");
        return responseMessage;
    }
}
