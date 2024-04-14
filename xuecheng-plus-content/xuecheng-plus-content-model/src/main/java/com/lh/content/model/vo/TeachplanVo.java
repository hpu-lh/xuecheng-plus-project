package com.lh.content.model.vo;

import com.lh.content.model.po.Teachplan;
import com.lh.content.model.po.TeachplanMedia;
import lombok.Data;

import java.util.List;

@Data
public class TeachplanVo extends Teachplan {

    //课程计划关联的媒资信息
    TeachplanMedia teachplanMedia;

    //子结点
    List<TeachplanVo> teachPlanTreeNodes;

}
