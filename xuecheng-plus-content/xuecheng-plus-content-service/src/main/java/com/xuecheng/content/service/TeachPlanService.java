package com.xuecheng.content.service;

import com.xuecheng.base.model.ErrorResult;
import com.xuecheng.base.model.Result;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.vo.TeachplanVo;

import java.util.List;

public interface TeachPlanService {

    public List<TeachplanVo> queryTeachPlanTreeByCourseId(Long courseId);

    public void saveTeachplan(SaveTeachplanDto teachplan);

    public ErrorResult deleteTeachPlanById(Long teachplanId);

    public Result<Object> moveupTeachPlanById(Long teachplanId);

    public Result<Object> movedownTeachPlanById(Long teachplanId);
}
