package com.gui.blog.service;

import com.gui.blog.dao.pojo.Article;
import com.gui.blog.vo.ArticleVo;
import com.gui.blog.vo.Result;
import com.gui.blog.vo.params.ArticleParam;
import com.gui.blog.vo.params.PageParams;

public interface ArticleService {
    /**
     * 首页文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 首页最新文章
     * @param limit
     * @return
     */
    Result newArticle(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchives();

    /**
     * 查看文章详情
     * @param id
     * @return
     */
    ArticleVo findArticleById(Long id);

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    Result publish(ArticleParam articleParam);
}
