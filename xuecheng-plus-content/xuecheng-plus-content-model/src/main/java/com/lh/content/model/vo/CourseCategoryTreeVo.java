package com.lh.content.model.vo;

import com.lh.content.model.po.CourseCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CourseCategoryTreeVo extends CourseCategory implements Serializable {


    private List<CourseCategoryTreeVo> childrenTreeNodes;

}
