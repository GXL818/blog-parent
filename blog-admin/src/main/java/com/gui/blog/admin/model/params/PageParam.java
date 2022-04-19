package com.gui.blog.admin.model.params;

import lombok.Data;

@Data
public class PageParam {


    private Integer pageSize;

    private Integer currentPage;

    private String queryString;
}
