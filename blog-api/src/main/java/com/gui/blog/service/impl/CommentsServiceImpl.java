package com.gui.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.gui.blog.dao.mapper.ArticleMapper;
import com.gui.blog.dao.mapper.CommentsMapper;
import com.gui.blog.dao.pojo.Article;
import com.gui.blog.dao.pojo.Comment;
import com.gui.blog.dao.pojo.SysUser;
import com.gui.blog.service.CommentsService;
import com.gui.blog.service.SysUserService;
import com.gui.blog.service.ThreadService;
import com.gui.blog.utils.UserThreadLocal;
import com.gui.blog.vo.CommentVo;
import com.gui.blog.vo.Result;
import com.gui.blog.vo.UserVo;
import com.gui.blog.vo.params.CommentParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private SysUserService sysUserService;
    @Override
    public Result commentsByArticleId(Long id) {
    LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Comment::getArticleId,id);
    wrapper.eq(Comment::getLevel,1);
          List<Comment> comments = commentsMapper.selectList(wrapper);
         List<CommentVo> commentVoList = copyList(comments);

    return Result.success(commentVoList);
    }

    @Override
    public Result comment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(Long.parseLong(commentParam.getArticleId()));
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentsMapper.insert(comment);
          LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
          wrapper.eq(Comment::getArticleId,commentParam.getArticleId());
        int count =  commentsMapper.selectCount(wrapper);
        Article article = new Article();
        article.setCommentCounts(count);
        article.setId(Long.parseLong(commentParam.getArticleId()));
        LambdaQueryWrapper<Article> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Article::getId,article.getId());
       articleMapper.update(article,wrapper1);
        return Result.success(null);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        commentVo.setId(String.valueOf(comment.getId()));
        BeanUtils.copyProperties(comment,commentVo);
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //作者信息
          Long authorId = comment.getAuthorId();
          UserVo userVo = this.sysUserService.findUserVoById(authorId);
          commentVo.setAuthor(userVo);
          //子评论
          if(comment.getLevel() == 1){
                Long id = comment.getId();
                List<CommentVo> commentVoList = findCommentsByParentId(id);
                commentVo.setChildrens(commentVoList);
          }
          //给谁评论
        if (comment.getLevel() > 1){
              Long toUid = comment.getToUid();
              UserVo toUserVo = sysUserService.findUserVoById(toUid);
              commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getParentId,id);
        wrapper.eq(Comment::getLevel,2);

        return copyList(commentsMapper.selectList(wrapper));
    }
}
