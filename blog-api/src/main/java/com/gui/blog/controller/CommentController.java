package com.gui.blog.controller;

import com.gui.blog.service.CommentsService;
import com.gui.blog.service.impl.CommentsServiceImpl;
import com.gui.blog.vo.Result;
import com.gui.blog.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentsService commentService;

  @GetMapping("/article/{id}")
  public Result comments(@PathVariable("id") Long id) {

    return commentService.commentsByArticleId(id);
    }

    @PostMapping("/create/change")
    public Result addComment(@RequestBody CommentParam commentParam) {

        return commentService.comment(commentParam);
    }
}