package com.gui.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gui.blog.dao.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热的标签前limit条
     * @param limit
     * @return
     */
    List<Long> finHotsTagIds(int limit);

    /**
     * 查询TagId获取的标签
     * @param tagIds
     * @return
     */
    List<Tag> findTagsByIds(List<Long> tagIds);
}
