package com.gui.blog.service;

import com.gui.blog.dao.pojo.SysUser;
import com.gui.blog.vo.Result;
import com.gui.blog.vo.UserVo;

public interface SysUserService {

    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
    SysUser finUserById(Long id);

    /**
     * 根据账号密码查询用户信息
     * @param account
     * @param password
     * @return
     */
    SysUser finUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result finUserByToken(String token);

    /**
     * 根据账户查询用户信息
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存
     * @param sysUser
     */
    void save(SysUser sysUser);

    /**
     * 根据userID查询用户信息
     * 封装user对象
     * @param id
     * @return
     */
    UserVo findUserVoById(Long id);
}




