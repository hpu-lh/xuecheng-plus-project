package com.xuecheng.content;


import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CourseTeacherServiceTest {

    @Autowired
    private CourseTeacherService courseTeacherService;

    @Test
    public void testGetCourseTeacherList(){
        List<CourseTeacher> courseTeacherList = courseTeacherService.queryCourseTeacherList(82L);
        System.out.println(courseTeacherList);

    }

    @Test
    public void testInsertCourseTeacherList(){
        CourseTeacher courseTeacher = new CourseTeacher();
        courseTeacher.setCourseId(82L);
        courseTeacher.setTeacherName("添加测试");
        courseTeacher.setPosition("高中");
        courseTeacher.setIntroduction("简单描述");
        CourseTeacher courseTeacher1 = courseTeacherService.insertCourseTeacher(courseTeacher);
        System.out.println(courseTeacher1);

    }
    @Test
    public void testUpdateCourseTeacher(){
        CourseTeacher courseTeacher = new CourseTeacher();
        courseTeacher.setId(28L);
        courseTeacher.setCourseId(82L);
        courseTeacher.setTeacherName("添加测试");
        courseTeacher.setPosition("大学");
        courseTeacher.setIntroduction("简单描述----修改");
        CourseTeacher courseTeacher1 = courseTeacherService.updataCourseTeacher(courseTeacher);
        System.out.println(courseTeacher1);

    }

}
