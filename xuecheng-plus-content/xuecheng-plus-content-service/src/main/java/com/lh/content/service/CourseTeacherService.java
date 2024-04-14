package com.lh.content.service;

import com.lh.base.model.Result;
import com.lh.content.model.po.CourseTeacher;

import java.util.List;

public interface CourseTeacherService {

    public List<CourseTeacher> queryCourseTeacherList(Long courseId);

    public CourseTeacher insertCourseTeacher(CourseTeacher courseTeacher);

    public CourseTeacher updataCourseTeacher(CourseTeacher courseTeacher);

    public Result<Object> deleteCourseTeacher(Long courseId,Long id);
}
