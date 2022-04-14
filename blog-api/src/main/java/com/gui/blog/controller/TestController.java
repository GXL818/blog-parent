package com.gui.blog.controller;

import com.gui.blog.dao.pojo.SysUser;
import com.gui.blog.utils.UserThreadLocal;
import com.gui.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

   @RequestMapping
    public Result hello() {
         SysUser sysUser = UserThreadLocal.get();
         return Result.success(sysUser);
   }
}
