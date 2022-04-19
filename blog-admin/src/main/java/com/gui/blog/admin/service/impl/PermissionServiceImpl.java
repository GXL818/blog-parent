package com.gui.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gui.blog.admin.mapper.PermissionMapper;
import com.gui.blog.admin.model.params.PageParam;
import com.gui.blog.admin.pojo.Permission;
import com.gui.blog.admin.service.PermissionService;
import com.gui.blog.admin.vo.PageResult;
import com.gui.blog.admin.vo.Result;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired

    private PermissionMapper permissionMapper;
    @Override
    public Result listPermission(PageParam pageParam) {

        Page<Permission> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
          LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();

          if( StringUtils.isNotBlank(pageParam.getQueryString())){
              wrapper.eq(Permission::getName,pageParam.getQueryString());
          }
          Page<Permission> permissionPage = permissionMapper.selectPage(page, wrapper);
        PageResult<Permission> pageResult = new PageResult<>();
        pageResult.setTotal(permissionPage.getTotal());
        pageResult.setList(permissionPage.getRecords());
        return Result.success(pageResult);
    }

    @Override
    public Result add(Permission permission) {
        permissionMapper.insert(permission);
        return Result.success(null);
    }

    @Override
    public Result update(Permission permission) {
        permissionMapper.updateById(permission);
        return Result.success(null);
    }

    @Override
    public Result delete(Long id) {
        permissionMapper.deleteById(id);
        return Result.success(null);
    }
}
