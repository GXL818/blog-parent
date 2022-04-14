package com.gui.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.gui.blog.dao.mapper.ArticleMapper;
import com.gui.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {
   //期望在线程池 执行 不会影响原有的主线程
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
          int viewCounts = article.getViewCounts();
          Article updateArticle = new Article();
        updateArticle.setViewCounts(viewCounts + 1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId, article.getId());
        //设置一个 为了多线程的环境下 线程安全
        updateWrapper.eq(Article::getViewCounts, viewCounts);
        //就是这种意思 类似CAS操作 乐观锁
        // update article set view_counts =  100 where view_counts =  99 and id = 1
          articleMapper.update(updateArticle,updateWrapper);
          try{
              Thread.sleep(3000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
    }
}
