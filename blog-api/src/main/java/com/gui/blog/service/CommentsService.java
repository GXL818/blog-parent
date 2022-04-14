package com.gui.blog.service;


import com.gui.blog.vo.Result;
import com.gui.blog.vo.params.CommentParam;

public interface CommentsService {
    /**
     * 根据文章id查询评论列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);

    /**
     * 添加评论
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);
}
