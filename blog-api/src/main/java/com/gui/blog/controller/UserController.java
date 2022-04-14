package com.gui.blog.controller;

import com.gui.blog.service.SysUserService;
import com.gui.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired

    private SysUserService sysUserService;

    /**
     * 通过token里的用户id 获取用户信息
     * @param token
     * @return
     */
    @GetMapping("/currentUser")
    public Result CurrentUser(@RequestHeader("Authorization") String token) {
        return sysUserService.finUserByToken(token);
    }
}
