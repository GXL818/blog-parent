package com.gui.blog.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gui.blog.admin.mapper.AdminMapper;
import com.gui.blog.admin.pojo.Admin;
import com.gui.blog.admin.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;


    public Admin findAdminByUserName(String username){
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername,username);
        wrapper.last("limit 1");
          Admin admin = adminMapper.selectOne(wrapper);
          return admin;
    }

    public List<Permission> findPermissionByAdminId(Long id) {

        return adminMapper.findPermissionByAdminId(id);

    }
}
