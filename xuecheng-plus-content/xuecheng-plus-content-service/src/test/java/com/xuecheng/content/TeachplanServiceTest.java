package com.xuecheng.content;


import com.xuecheng.base.model.ErrorResult;
import com.xuecheng.base.model.Result;
import com.xuecheng.content.service.TeachPlanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TeachplanServiceTest {

    @Autowired
    private TeachPlanService teachPlanService;

    @Test
    public void testDeleteTeachplan(){

        ErrorResult stringErrorResult = teachPlanService.deleteTeachPlanById(278L);
        System.out.println(stringErrorResult);
    }

    @Test
    public void testMoveupTeachplan(){
        Result<Object> objectResult = teachPlanService.moveupTeachPlanById(269L);
        System.out.println(objectResult);
    }
}
