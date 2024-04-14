package com.lh.content.model.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;
@Data
@ToString
public class CoursePreviewVo {
    //课程基本信息,课程营销信息
    CourseBaseInfoVo courseBase;


    //课程计划信息
    List<TeachplanVo> teachplans;

    //师资信息暂时不加...

}
