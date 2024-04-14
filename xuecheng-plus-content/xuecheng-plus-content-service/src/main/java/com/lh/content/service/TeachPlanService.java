package com.lh.content.service;

import com.lh.base.model.ErrorResult;
import com.lh.base.model.Result;
import com.lh.content.model.dto.BindTeachplanMediaDto;
import com.lh.content.model.dto.SaveTeachplanDto;
import com.lh.content.model.po.TeachplanMedia;
import com.lh.content.model.vo.TeachplanVo;

import java.util.List;

public interface TeachPlanService {

    public List<TeachplanVo> queryTeachPlanTreeByCourseId(Long courseId);

    public void saveTeachplan(SaveTeachplanDto teachplan);

    public ErrorResult deleteTeachPlanById(Long teachplanId);

    public Result<Object> moveupTeachPlanById(Long teachplanId);

    public Result<Object> movedownTeachPlanById(Long teachplanId);

    public TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto);


}
