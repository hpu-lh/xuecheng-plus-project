package com.lh.content;


import com.lh.content.model.vo.CourseCategoryTreeVo;
import com.lh.content.service.CourseCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CourseCategoryServiceTest {

    @Autowired
    private CourseCategoryService courseCategoryService;

    @Test
    public void testQuearyCourseCategoryTree(){
        List<CourseCategoryTreeVo> courseCategoryTreeVos = courseCategoryService.queryCourseCategoryTree("1");
        System.out.println(courseCategoryTreeVos);

    }
}
