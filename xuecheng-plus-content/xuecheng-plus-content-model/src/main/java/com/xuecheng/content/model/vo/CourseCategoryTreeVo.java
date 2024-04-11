package com.xuecheng.content.model.vo;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CourseCategoryTreeVo extends CourseCategory implements Serializable {


    private List<CourseCategoryTreeVo> childrenTreeNodes;

}
