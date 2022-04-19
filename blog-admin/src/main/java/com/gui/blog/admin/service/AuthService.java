package com.gui.blog.admin.service;

import com.gui.blog.admin.pojo.Admin;
import com.gui.blog.admin.pojo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AuthService {

  @Autowired
  private AdminService adminService;

  public boolean auth(HttpServletRequest request, Authentication authentication) {
    // 权限认证
    // 请求路径
    String requestURI = request.getRequestURI();
    Object principal = authentication.getPrincipal();
    if (principal.equals("anonymousUser") || principal == null) {
      return false;
    }
    UserDetails userDetails = (UserDetails) principal;
    String username = userDetails.getUsername();
    Admin admin = adminService.findAdminByUserName(username);
    if (admin == null) {
      return false;
    }
    Long id = admin.getId();
    if (id == 1) {
        //超级管理员
      return true;
    }
    List<Permission> permissionList = adminService.findPermissionByAdminId(id);
    requestURI = StringUtils.split(requestURI, '?')[0];
    System.out.println(requestURI);
    for (Permission permission : permissionList) {
      if (requestURI.equals(permission.getPath())) {
        return true;
      }
    }
      return false;
  }
}
