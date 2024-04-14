package com.lh.content.service;

import com.lh.content.model.vo.CourseCategoryTreeVo;

import java.util.List;

public interface CourseCategoryService {

    public List<CourseCategoryTreeVo> queryCourseCategoryTree(String id);
}
