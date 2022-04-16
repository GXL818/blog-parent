package com.gui.blog.vo.params;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class PageParams {
 //总页数
  private int pageSize = 10;
//当前页
  private int page = 1;

  @JsonSerialize(using = ToStringSerializer.class)
  private Long categoryId;

  private Long TagId;

  private String month;

  private String year;

  public String getMonth(){
      if(this.month != null && this.month.length() == 1){
          return "0" + this.month;
      }
      return this.month;
  }
}
