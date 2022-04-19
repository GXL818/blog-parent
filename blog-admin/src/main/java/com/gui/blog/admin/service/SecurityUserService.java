package com.gui.blog.admin.service;

import com.gui.blog.admin.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SecurityUserService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

          Admin admin = adminService.findAdminByUserName(username);
          if(admin == null) {
              return null;
          }
            UserDetails userDetails = new User(username, admin.getPassword(),new ArrayList<>());
        return   userDetails;
    }
}





