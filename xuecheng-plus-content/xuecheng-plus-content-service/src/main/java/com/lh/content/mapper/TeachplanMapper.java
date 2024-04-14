package com.lh.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.content.model.po.Teachplan;
import com.lh.content.model.vo.TeachplanVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TeachplanMapper extends BaseMapper<Teachplan> {
    List<TeachplanVo> selectTeachPlanTree(Long courseId);

    List<Teachplan> selectChildTeachPlanByParentId(Long teachplanId);

    Integer selectMaxOrderFromTeachplanByCourseIdAndParentId(@Param("courseId") Long courseId,@Param("parentId") Long parentId);
    Integer selectMinOrderFromTeachplanByCourseIdAndParentId(@Param("courseId") Long courseId,@Param("parentId") Long parentId);




    List<Teachplan> selectUpTeachplan(@Param("courseId") Long courseId, @Param("parentId") Long parentId,@Param("orderBy") Long orderBy);

    List<Teachplan> selectDownTeachplan(@Param("courseId") Long courseId, @Param("parentId") Long parentId,@Param("orderBy") Long orderBy);


}
