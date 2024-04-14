package com.lh.content;


import com.lh.content.mapper.CourseTeacherMapper;
import com.lh.content.model.po.CourseTeacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CourseTeacherMapperTests {

    @Autowired
    private CourseTeacherMapper courseTeacherMapper;

    @Test
    public void testInsertCourseTeacher(){
        CourseTeacher courseTeacher = new CourseTeacher();
        courseTeacher.setCourseId(82L);
        courseTeacher.setTeacherName("添加测试");
        courseTeacher.setPosition("高中");
        courseTeacher.setIntroduction("简单描述");
        Long id=courseTeacherMapper.insertCourseTeacher(courseTeacher);
        System.out.println(courseTeacher.getId());


    }
}
