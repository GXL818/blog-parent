package com.gui.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gui.blog.dao.mapper.TagMapper;
import com.gui.blog.dao.pojo.Tag;
import com.gui.blog.service.TagService;
import com.gui.blog.vo.Result;
import com.gui.blog.vo.TagVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
    List<Tag> tags =   tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    /**
     * 最热标签
     * @param limit
     * @return
     */
    @Override
    public Result hots(int limit) {
        /**
         * 1. 标签所拥有的文章数量最多 最热标签
         *
         * 2.查询 根据 tag_id 分组 计数 从小到大 排序 取前limit个
         */
      List<Long> tagIds =   tagMapper.finHotsTagIds(limit);
    System.out.println(tagIds);
      if(CollectionUtils.isEmpty(tagIds)){
      return Result.success(Collections.emptyList());
      }
        List<Tag> tags = tagMapper.findTagsByIds(tagIds);
        return Result.success(tags);
    }

    @Override
    public Result findAll() {
          List<Tag> tags = tagMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(tags));
    }
}
