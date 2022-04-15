package com.gui.blog.controller;

import com.gui.blog.service.CategoryService;
import com.gui.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取文章内容标签
     * @return
     */
    @GetMapping
    public Result getCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/detail")
    public Result getCategoryDetail() {
        return categoryService.findAllDetail();
    }

    @GetMapping("/detail/{id}")
    public Result CategoryDetailById(@PathVariable("id") Long id) {

        return categoryService.CategoryDetailById(id);
    }


}
