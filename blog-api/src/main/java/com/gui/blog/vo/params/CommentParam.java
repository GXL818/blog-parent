package com.gui.blog.vo.params;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class CommentParam {
    private String articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}