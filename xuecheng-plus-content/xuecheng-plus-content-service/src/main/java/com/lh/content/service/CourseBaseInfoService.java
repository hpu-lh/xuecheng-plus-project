package com.lh.content.service;

import com.lh.base.model.PageParams;
import com.lh.base.model.PageResult;
import com.lh.base.model.Result;
import com.lh.content.model.dto.AddCourseDto;
import com.lh.content.model.dto.EditCourseDto;
import com.lh.content.model.dto.QueryCourseParamsDto;
import com.lh.content.model.po.CourseBase;
import com.lh.content.model.vo.CourseBaseInfoVo;

public interface CourseBaseInfoService {


    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);

    public CourseBaseInfoVo insertCourseBase(Long companyId,AddCourseDto addCourseDto);

    public CourseBaseInfoVo getCourseBaseInfo(Long courseId);

    public CourseBaseInfoVo updateCourseBaseInfo(Long companyId, EditCourseDto editCourseDto);

    public Result<Object> deleteCourse(Long courseId);

}
