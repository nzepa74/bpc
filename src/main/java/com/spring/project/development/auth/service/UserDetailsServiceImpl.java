package com.spring.project.development.auth.service;


import com.spring.project.development.helper.LoginErrorCode;
import com.spring.project.development.helper.Permission;
import com.spring.project.development.voler.dao.PermissionDao;
import com.spring.project.development.voler.dto.PermissionListDTO;
import com.spring.project.development.voler.entity.sa.SaUser;
import com.spring.project.development.voler.repository.sa.SaUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PermissionDao permissionDao;
    private final SaUserRepository saUserRepository;
    @Autowired
    @Qualifier("messageSource")
    private MessageSource messageSource;

    public UserDetailsServiceImpl(PermissionDao permissionDao, SaUserRepository saUserRepository) {
        this.permissionDao = permissionDao;
        this.saUserRepository = saUserRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        SaUser saUser = saUserRepository.findByUsername(username);
        if (saUser == null) {
            saUser = saUserRepository.findByEmail(username);
        }
//        log.info(messageSource.getMessage("userSetupMessage.noDataFound", null, null));
        if (saUser == null) throw new UsernameNotFoundException(LoginErrorCode.FAILED.getCode());
        else if (!saUser.getStatus().equals('A')) throw new LockedException(LoginErrorCode.LOCKED.getCode());
        Collection<GrantedAuthority> grantedAuthorities = getAccessRight(saUser);
        return new org.springframework.security.core.userdetails.User(saUser.getUsername(), saUser.getPassword(), grantedAuthorities);
    }

    private Collection<GrantedAuthority> getAccessRight(SaUser saUser) {

        Collection<GrantedAuthority> authorities = new HashSet<>();
        List<PermissionListDTO> permissionListDTOS = permissionDao.getRoleMappedScreens(saUser.getRoleId());
        permissionListDTOS.forEach(permissionListDTO -> {
            Integer screenId = permissionListDTO.getScreenId();

            //Screen permissions
            if (permissionListDTO.getViewAllowed() != null && permissionListDTO.getViewAllowed() == 'Y') {
                authorities.add(new SimpleGrantedAuthority(screenId + "-" + Permission.VIEW));
            }
            if (permissionListDTO.getDeleteAllowed() != null && permissionListDTO.getDeleteAllowed() == 'Y') {
                authorities.add(new SimpleGrantedAuthority(screenId + "-" + Permission.DELETE));

            }
            if (permissionListDTO.getSaveAllowed() != null && permissionListDTO.getSaveAllowed() == 'Y') {
                authorities.add(new SimpleGrantedAuthority(screenId + "-" + Permission.ADD));
            }
            if (permissionListDTO.getEditAllowed() != null && permissionListDTO.getEditAllowed() == 'Y') {
                authorities.add(new SimpleGrantedAuthority(screenId + "-" + Permission.EDIT));
            }
        });
        return authorities;
    }
}
