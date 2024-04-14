package com.lh.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lh.content.service.CourseTeacherService;
import com.lh.base.model.Result;
import com.lh.content.mapper.CourseTeacherMapper;
import com.lh.content.model.po.CourseTeacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {

    @Autowired
    private CourseTeacherMapper courseTeacherMapper;
    @Override
    public List<CourseTeacher> queryCourseTeacherList(Long courseId) {
        LambdaQueryWrapper<CourseTeacher> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.eq(CourseTeacher::getCourseId,courseId);
        List<CourseTeacher> courseTeacherList = courseTeacherMapper.selectList(objectLambdaQueryWrapper);
        return courseTeacherList;
    }


    @Override
    public CourseTeacher insertCourseTeacher(CourseTeacher courseTeacher) {
        courseTeacher.setCreateDate(LocalDateTime.now());
        Long id=courseTeacherMapper.insertCourseTeacher(courseTeacher);
        CourseTeacher courseTeacher1 = courseTeacherMapper.selectById(courseTeacher.getId());
        return courseTeacher1;
    }

    @Transactional
    @Override
    public CourseTeacher updataCourseTeacher(CourseTeacher courseTeacher) {
        Long id=courseTeacher.getId();
        courseTeacherMapper.updateById(courseTeacher);
        return courseTeacherMapper.selectById(id);
    }

    @Override
    public Result<Object> deleteCourseTeacher(Long courseId, Long id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("course_id",courseId);
        map.put("id",id);

        courseTeacherMapper.deleteByMap(map);
        return Result.ok("删除成功",null);
    }
}
