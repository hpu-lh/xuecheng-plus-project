package com.xuecheng.content;


import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.vo.TeachplanVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TeachPlanMapperTests {

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Test
    public void testCourseBseMapper(){

        List<TeachplanVo> teachplanVos = teachplanMapper.selectTeachPlanTree(117L);

        System.out.println(teachplanVos);


    }

    @Test
    public void testTeachPlanMapper(){
        List<Teachplan> teachplans = teachplanMapper.selectChildTeachPlanByParentId(295L);
        System.out.println(teachplans);


    }
}
