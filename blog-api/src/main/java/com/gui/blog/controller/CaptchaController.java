package com.gui.blog.controller;

import com.gui.blog.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @PostMapping
    public Result captcha() {
        int mobile_code = (int)((Math.random()*9+1)*100000);
        return Result.success(mobile_code);
    }
}
