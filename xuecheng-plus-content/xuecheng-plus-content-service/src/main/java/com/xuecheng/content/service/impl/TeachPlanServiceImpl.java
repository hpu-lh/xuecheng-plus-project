package com.xuecheng.content.service.impl;

import com.xuecheng.base.model.ErrorResult;
import com.xuecheng.base.model.Result;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.mapper.TeachplanMediaMapper;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.vo.TeachplanVo;
import com.xuecheng.content.service.TeachPlanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class TeachPlanServiceImpl implements TeachPlanService {
    @Autowired
    private TeachplanMapper teachplanMapper;

    @Autowired
    private TeachplanMediaMapper teachplanMediaMapper;
    @Override
    public List<TeachplanVo> queryTeachPlanTreeByCourseId(Long courseId) {

        return teachplanMapper.selectTeachPlanTree(courseId);
    }

    @Override
    public void saveTeachplan(SaveTeachplanDto teachplan) {
        Long teachplanId = teachplan.getId();
        if(teachplanId==null){
            Teachplan teachplan1 = new Teachplan();
            BeanUtils.copyProperties(teachplan,teachplan1);
            Long courseId = teachplan1.getCourseId();
            Long parentid = teachplan1.getParentid();
            Integer order = teachplanMapper.selectMaxOrderFromTeachplanByCourseIdAndParentId(courseId, parentid);
            if(order==null) order=0;
            teachplan1.setOrderby(order+1);
            teachplan1.setCreateDate(LocalDateTime.now());
            teachplanMapper.insert(teachplan1);
        }else{
            Teachplan teachplan1 = teachplanMapper.selectById(teachplanId);
            BeanUtils.copyProperties(teachplan,teachplan1);
            teachplan1.setChangeDate(LocalDateTime.now());
            int i = teachplanMapper.updateById(teachplan1);
        }
    }


    @Transactional
    @Override
    public ErrorResult deleteTeachPlanById(Long teachplanId) {
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        if(teachplan==null){
            return ErrorResult.create("120409","课程计划不存在");
        }

        if(teachplan.getParentid()==0){
            List<Teachplan> teachplanList = teachplanMapper.selectChildTeachPlanByParentId(teachplanId);
            if(teachplanList.size()>0){
                return ErrorResult.create("120409","课程计划信息还有子级信息，无法操作");
            }else{
                int i = teachplanMapper.deleteById(teachplanId);
                if(i>0){
                    return ErrorResult.create("200","删除成功");
                }else{
                    return ErrorResult.create("120409","删除失败");
                }
            }
        }else{
            teachplanMediaMapper.deleteTeachplanMediaByTeachplanId(teachplanId);
            teachplanMapper.deleteById(teachplanId);
            return ErrorResult.create("200","删除成功");
        }
    }

    @Transactional
    @Override
    public Result<Object> moveupTeachPlanById(Long teachplanId) {
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        if(teachplan==null){
            return Result.err("课程计划不存在",null);
        }
        Long parentid = teachplan.getParentid();
        Long courseId = teachplan.getCourseId();
        Long orderby = Long.valueOf(teachplan.getOrderby());
        Long minOrder = Long.valueOf(teachplanMapper.selectMinOrderFromTeachplanByCourseIdAndParentId(courseId, parentid));

        if(orderby.equals(minOrder)){
            return Result.ok("该课程计划已经在最上面",null);
        }else{
            List<Teachplan> teachplanList = teachplanMapper.selectUpTeachplan(courseId, parentid, orderby);
            Teachplan teachplanUp = teachplanList.get(teachplanList.size() - 1);
            Long temp= Long.valueOf(teachplanUp.getOrderby());
            teachplanUp.setOrderby(teachplan.getOrderby());
            teachplan.setOrderby(Math.toIntExact(temp));
            teachplanMapper.updateById(teachplanUp);
            teachplanMapper.updateById(teachplan);
            return Result.ok("课程计划上移成功",null);
        }
    }

    @Transactional
    @Override
    public Result<Object> movedownTeachPlanById(Long teachplanId) {
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        if(teachplan==null){
            return Result.err("课程计划不存在",null);
        }
        Long parentid = teachplan.getParentid();
        Long courseId = teachplan.getCourseId();
        Long orderby = Long.valueOf(teachplan.getOrderby());
        Long maxOrder = Long.valueOf(teachplanMapper.selectMaxOrderFromTeachplanByCourseIdAndParentId(courseId, parentid));

        if(orderby.equals(maxOrder)){
            return Result.ok("该课程计划已经在最下面",null);
        }else{
            List<Teachplan> teachplanList = teachplanMapper.selectDownTeachplan(courseId, parentid, orderby);
            Teachplan teachplanDown = teachplanList.get(0);
            Long temp= Long.valueOf(teachplanDown.getOrderby());
            teachplanDown.setOrderby(teachplan.getOrderby());
            teachplan.setOrderby(Math.toIntExact(temp));
            teachplanMapper.updateById(teachplanDown);
            teachplanMapper.updateById(teachplan);
            return Result.ok("课程计划下移成功",null);
        }
    }
}
