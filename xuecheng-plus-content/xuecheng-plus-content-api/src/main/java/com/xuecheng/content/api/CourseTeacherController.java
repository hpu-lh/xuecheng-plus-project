package com.xuecheng.content.api;

import com.xuecheng.base.model.Result;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseTeacherController {
    @Autowired
    private CourseTeacherService courseTeacherService;

    @GetMapping("/courseTeacher/list/{courseId}")
    public List<CourseTeacher> getCourseTeacherList(@PathVariable Long courseId){
        return courseTeacherService.queryCourseTeacherList(courseId);
    }

    @PostMapping("/courseTeacher")
    public CourseTeacher insertCourseTeacher(@RequestBody CourseTeacher courseTeacher){

        return courseTeacherService.insertCourseTeacher(courseTeacher);
    }

    @PutMapping("/courseTeacher")
    public CourseTeacher updataCourseTeacher(@RequestBody CourseTeacher courseTeacher){
        return courseTeacherService.updataCourseTeacher(courseTeacher);

    }

    @DeleteMapping("/courseTeacher/course/{courseId}/{id}")
    public Result<Object> deleteCourseTeacher(@PathVariable("courseId") Long courseId,@PathVariable("id") Long id){
        return courseTeacherService.deleteCourseTeacher(courseId,id);

    }


}
