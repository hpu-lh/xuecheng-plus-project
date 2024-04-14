package com.lh.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.content.model.po.CourseCategory;
import com.lh.content.model.vo.CourseCategoryTreeVo;

import java.util.List;

/**
 * <p>
 * 课程分类 Mapper 接口
 * </p>
 *
 */
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    public List<CourseCategoryTreeVo> queryCourseCategoryTree(String id);

}
