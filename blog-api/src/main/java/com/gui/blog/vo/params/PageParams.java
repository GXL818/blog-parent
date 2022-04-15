package com.gui.blog.vo.params;

import lombok.Data;

@Data
public class PageParams {
 //总页数
  private int pageSize = 10;
//当前页
  private int page = 1;

  private Long categoryId;

  private Long TagId;
}
