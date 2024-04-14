package com.lh.content.api;

import com.lh.base.exception.ValidationGroups;
import com.lh.base.model.PageParams;
import com.lh.base.model.PageResult;
import com.lh.base.model.Result;
import com.lh.content.model.dto.AddCourseDto;
import com.lh.content.model.dto.EditCourseDto;
import com.lh.content.model.dto.QueryCourseParamsDto;
import com.lh.content.model.po.CourseBase;
import com.lh.content.model.vo.CourseBaseInfoVo;
import com.lh.content.service.CourseBaseInfoService;
import com.lh.content.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sun.plugin2.message.GetAuthenticationMessage;

import java.security.Security;

@Api(value = "课程信息管理接口",tags = "课程信息管理接口")
@RestController
public class CourseBaseInfoController {

    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @ApiOperation("课程查询接口")
    @PreAuthorize("hasAuthority('xc_teachmanager_course_list ')")
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
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(principal);
        SecurityUtil.XcUser user = SecurityUtil.getUser();
        System.out.println("================================"+user.getName());
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
