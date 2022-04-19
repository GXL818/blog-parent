package com.gui.blog.admin.service;

import com.gui.blog.admin.model.params.PageParam;
import com.gui.blog.admin.pojo.Permission;
import com.gui.blog.admin.vo.Result;

public interface PermissionService {
    Result listPermission(PageParam pageParam);

    Result add(Permission permission);

    Result update(Permission permission);

    Result delete(Long id);
}
