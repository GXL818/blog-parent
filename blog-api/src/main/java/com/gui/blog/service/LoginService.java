package com.gui.blog.service;

import com.gui.blog.dao.pojo.SysUser;
import com.gui.blog.vo.Result;
import com.gui.blog.vo.params.LoginParams;

public interface LoginService {
    /**
     * 用户登录
     * @param loginParams
     * @return
     */
    Result login(LoginParams loginParams);

    /**
     * 检查token是否有效
     * @param token
     * @return
     */
    SysUser checkToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册
     * @param loginParams
     * @return
     */
    Result register(LoginParams loginParams);
}
