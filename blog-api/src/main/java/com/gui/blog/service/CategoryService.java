package com.gui.blog.service;

import com.gui.blog.vo.CategoryVo;
import com.gui.blog.vo.Result;

public interface CategoryService {
    /**
     * 根据类别查询
     * @param id
     * @return
     */
    CategoryVo findCategoryById(Long id);


    /**
     *
     * @return
     */
    Result findAll();

    /**
     * 查询所有分类Detail
     * @return
     */
    Result findAllDetail();

    /**
     * 根据id查询分类信息
     * @return
     */
    Result CategoryDetailById(Long id);


}
