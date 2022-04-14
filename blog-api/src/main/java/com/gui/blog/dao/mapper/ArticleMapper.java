package com.gui.blog.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gui.blog.dao.dos.Archives;
import com.gui.blog.dao.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    List<Archives> listArchives();
}
