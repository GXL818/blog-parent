package com.gui.blog.controller;

import com.gui.blog.service.TagService;
import com.gui.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
public class TagsController {
    @Autowired
    private TagService tagService;

    /**
     * 获取最热所有标签
     * @return
     */
    @GetMapping("hot")
    public Result getTags() {
        int limit = 6;
        return tagService.hots(limit);
    }
   @GetMapping
    public Result findAll() {
        return tagService.findAll();
    }
}
