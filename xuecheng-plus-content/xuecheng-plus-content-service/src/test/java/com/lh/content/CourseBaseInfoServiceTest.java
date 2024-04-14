package com.lh.content;


import com.lh.content.service.CourseBaseInfoService;
import com.lh.base.model.PageParams;
import com.lh.base.model.PageResult;
import com.lh.content.model.dto.QueryCourseParamsDto;
import com.lh.content.model.po.CourseBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CourseBaseInfoServiceTest {

    @Autowired
    private CourseBaseInfoService courseBaseInfoService;

    @Test
    public void testQuearyCourselist(){

        PageParams pageParams = new PageParams(1, 2);
        QueryCourseParamsDto queryCourseParamsDto = new QueryCourseParamsDto();
//        queryCourseParamsDto.setCourseName("java");

//        queryCourseParamsDto.setAuditStatus("202004");
        queryCourseParamsDto.setPublishStatus("203001");
        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseList(pageParams, queryCourseParamsDto);

        System.out.println(courseBasePageResult);


    }
}
