package com.gui.blog.service;

import com.gui.blog.dao.pojo.Tag;
import com.gui.blog.vo.Result;
import com.gui.blog.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);
    /**
     * 获取最热所有标签
     * @return
     */
    Result hots(int limit);
    /**
     * 获取所有文章标签
     * @return
     */
    Result findAll();

    /**
     * 查询所有文章标签详情
     * @return
     */
    Result findAllDetail();


    Result findDetailById(Long id);
}
