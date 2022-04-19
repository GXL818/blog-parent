package com.gui.blog.controller;
import com.gui.blog.common.aop.LogAnnotation;
import com.gui.blog.common.cache.Cache;
import com.gui.blog.service.ArticleService;
import com.gui.blog.vo.Result;
import com.gui.blog.vo.params.ArticleParam;
import com.gui.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;
    /**
     * 首页文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    @LogAnnotation(module = "首页文章列表",operator = "获取文章列表")

    public Result listArticle(@RequestBody PageParams pageParams){
        return articleService.listArticle(pageParams);
}

    /**
     * 首页最热文章
     * @param
     * @return
     */

    @PostMapping("hot")
    @Cache(expire = 5*60*1000,name = "hot_article")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }


    /**
     *首页最新文章
     * @return
     */
    @PostMapping("new")

    public Result newArticle(){
        int limit = 5;
        return articleService.newArticle(limit);
    }

    /**
     * 文章归档
     * @return
     */
    @PostMapping("listArchives")
    public Result archives(){
        return articleService.listArchives();
    }


    @PostMapping("/view/{id}")
    public Result ArticleView(@PathVariable("id") Long id){
        return  Result.success(articleService.findArticleById(id));
    }

    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }
}
