package com.xuecheng.content.service;

import com.xuecheng.content.model.vo.CourseCategoryTreeVo;

import java.util.List;

public interface CourseCategoryService {

    public List<CourseCategoryTreeVo> queryCourseCategoryTree(String id);
}
