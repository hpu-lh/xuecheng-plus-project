package com.lh.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.content.model.po.CourseTeacher;


public interface CourseTeacherMapper extends BaseMapper<CourseTeacher> {

    public Long insertCourseTeacher(CourseTeacher courseTeacher);

}
