package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.base.model.Result;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.vo.CourseBaseInfoVo;

public interface CourseBaseInfoService {


    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);

    public CourseBaseInfoVo insertCourseBase(Long companyId,AddCourseDto addCourseDto);

    public CourseBaseInfoVo getCourseBaseInfo(Long courseId);

    public CourseBaseInfoVo updateCourseBaseInfo(Long companyId, EditCourseDto editCourseDto);

    public Result<Object> deleteCourse(Long courseId);

}
