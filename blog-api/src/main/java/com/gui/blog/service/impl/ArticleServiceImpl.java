package com.gui.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gui.blog.dao.dos.Archives;
import com.gui.blog.dao.mapper.ArticleBodyMapper;
import com.gui.blog.dao.mapper.ArticleMapper;
import com.gui.blog.dao.mapper.ArticleTagMapper;
import com.gui.blog.dao.pojo.*;
import com.gui.blog.service.*;
import com.gui.blog.utils.UserThreadLocal;
import com.gui.blog.vo.*;
import com.gui.blog.vo.params.ArticleParam;
import com.gui.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    /**
     * 首页文章列表
     * @param pageParams
     * @return
     */
    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if(pageParams.getCategoryId() != null){
    queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
        }
        List<Long> articleIdList = new ArrayList<>();
        if(pageParams.getTagId() != null){
            LambdaQueryWrapper<ArticleTag> articleTagWrapper = new LambdaQueryWrapper<>();
            articleTagWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
              List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagWrapper);
      for (ArticleTag articleTag : articleTags) {
          articleIdList.add(articleTag.getArticleId());
      }
      if(articleIdList.size() > 0){
          queryWrapper.in(Article::getId,articleIdList);
      }
        }
        //是否进行排序
        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
         Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
          List<Article> records = articlePage.getRecords();
          List<ArticleVo> articleVoList = copyList(records,true,true);
        return Result.success(articleVoList);
    }
    /**
     * 最热文章
     * @param limit
     * @return
     */
    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit " + limit);
        //select id,title from article order by view_counts desc limit 5
          List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }
    /**
     * 首页最新文章
     * @param limit
     * @return
     */
    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> Wrapper = new LambdaQueryWrapper<>();
        Wrapper.orderByDesc(Article::getCreateDate);
        Wrapper.select(Article::getId,Article::getTitle);
        Wrapper.last("limit " + limit);
        //select id,title from article order by create_date desc limit 5
        List<Article> articles = articleMapper.selectList(Wrapper);
        return Result.success(copyList(articles,false,false));
    }

    /**
     * 文章归档
     * @return
     */
    @Override
    public Result listArchives() {
       List<Archives>  archives =  articleMapper.listArchives();
        return Result.success(archives);
    }

    /**
     * 文章详情
     * @param id
     * @return
     */

    @Autowired
    private CategoryService    categoryService;

    @Autowired
    private ThreadService   threadService;

    @Override
    public ArticleVo findArticleById(Long id) {
          Article article = articleMapper.selectById(id);
        ArticleVo articleVo = copy(article, true, true,true,true);
        /**
         * 更新阅读数量
         */
        threadService.updateArticleViewCount(articleMapper,article);
        return articleVo;
    }

    @Override
    public Result publish(ArticleParam articleParam) {
          SysUser sysUser = UserThreadLocal.get();
          Article article = new Article();

          article.setAuthorId(sysUser.getId());
          article.setWeight(Article.Article_Common);
          article.setCreateDate(System.currentTimeMillis());
          article.setViewCounts(0);
          article.setTitle(articleParam.getTitle());
          article.setSummary(articleParam.getSummary());
          article.setCommentCounts(0);
          article.setCategoryId(articleParam.getCategory().getId());

          articleMapper.insert(article);
          List<TagVo> tags = articleParam.getTags();
          if(tags!=null && tags.size()>0){
          for (TagVo tag : tags) {
              Long articleId = article.getId();
              ArticleTag articleTag = new ArticleTag();
              articleTag.setTagId(tag.getId());
              articleTag.setArticleId(articleId);
              articleTagMapper.insert(articleTag);
        //
          }
          }
          ArticleBody articleBody = new ArticleBody();
          articleBody.setArticleId(article.getId());
          articleBody.setContent(articleParam.getBody().getContent());
          articleBody.setContentHtml(articleParam.getBody().getContentHtml());
          articleBodyMapper.insert(articleBody);
          article.setBodyId(articleBody.getId());
          articleMapper.updateById(article);
          Map<String,String> map = new HashMap<>();
          map.put("id",article.getId().toString());

        return Result.success(map);
    }

    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    private ArticleBodyVo findArticleBodyById(Long id) {
        ArticleBody articleBody = articleBodyMapper.selectById(id);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }


    private List<ArticleVo> copyList(List<Article> records ,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory ){
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : records) {
            articleVoList.add(copy(article,isTag,isAuthor,isBody,isCategory));
        }
        return articleVoList;
    }

    /**
     * 方法重载
     * @param records
     * @param isTag
     * @param isAuthor
     * @return
     */
    private List<ArticleVo> copyList(List<Article> records ,boolean isTag,boolean isAuthor  ){
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : records) {
            articleVoList.add(copy(article,isTag,isAuthor,false,false));
        }
        return articleVoList;
    }
    public ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory ){
            ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        if(isTag){
           articleVo.setTags(tagService.findTagsByArticleId(article.getId()));
        }
        if(isAuthor){
            //作者iD
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.finUserById(authorId).getNickname());
        }
        if (isBody){
            ArticleBodyVo articleBody = findArticleBodyById(article.getBodyId());
            articleVo.setBody(articleBody);
        }
        if (isCategory){
            CategoryVo categoryVo = categoryService.findCategoryById(article.getCategoryId());
            articleVo.setCategory(categoryVo);
        }

        return articleVo;
    }



}
