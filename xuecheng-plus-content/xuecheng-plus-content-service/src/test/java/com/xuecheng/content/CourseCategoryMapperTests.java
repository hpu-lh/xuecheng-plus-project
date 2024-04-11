package com.xuecheng.content;


import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.vo.CourseCategoryTreeVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CourseCategoryMapperTests {

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Test
    public void testCourseCategoryMapper(){
        List<CourseCategoryTreeVo> courseCategoryTreeVos = courseCategoryMapper.queryCourseCategoryTree("1");
        System.out.println(courseCategoryTreeVos);


    }
}
