package com.lh.content;


import com.lh.content.mapper.CourseBaseMapper;
import com.lh.content.model.po.CourseBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CourseBaseMapperTests {

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Test
    public void testCourseBseMapper(){

        CourseBase courseBase = courseBaseMapper.selectById(1);
        System.out.println(courseBase);


    }
}
