package com.xuecheng.content.api;

import com.xuecheng.base.exception.ValidationGroups;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.base.model.Result;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.vo.CourseBaseInfoVo;
import com.xuecheng.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "课程信息管理接口",tags = "课程信息管理接口")
@RestController
public class CourseBaseInfoController {

    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @ApiOperation("课程查询接口")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required=false) QueryCourseParamsDto queryCourseParamsDto) {

        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseList(pageParams, queryCourseParamsDto);

        return courseBasePageResult;

    }
    @ApiOperation("新增课程")
    @PostMapping("/course")
    public CourseBaseInfoVo createCourseBase(@RequestBody @Validated(ValidationGroups.Inster.class) AddCourseDto addCourseDto){
        //获取到用户所属机构的id
        Long companyId = 1232141425L;
        CourseBaseInfoVo courseBaseInfoVo = courseBaseInfoService.insertCourseBase(companyId, addCourseDto);
        return courseBaseInfoVo;
    }

    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoVo getCourseBaseById(@PathVariable Long courseId){
        CourseBaseInfoVo courseBaseInfoVo = courseBaseInfoService.getCourseBaseInfo(courseId);
        return courseBaseInfoVo;
    }


    @ApiOperation("修改课程")
    @PutMapping("/course")
    public CourseBaseInfoVo updateCourseBaseInfo(@RequestBody @Validated(ValidationGroups.Update.class) EditCourseDto editCourseDto){

        Long companyId = 1232141425L;
        CourseBaseInfoVo courseBaseInfoVo = courseBaseInfoService.updateCourseBaseInfo(companyId, editCourseDto);
        return courseBaseInfoVo;
    }

    @DeleteMapping("/course/{courseId}")
    public Result<Object> deleteCourse(@PathVariable Long courseId){
        return courseBaseInfoService.deleteCourse(courseId);
    }

}
