package com.gui.blog.dao.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class Comment {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String content;

    private Long createDate;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long articleId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorId;


    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    private Long toUid;

    private Integer level;
}