package com.lh.content.api;

import com.lh.base.model.ErrorResult;
import com.lh.base.model.Result;
import com.lh.content.model.dto.BindTeachplanMediaDto;
import com.lh.content.model.dto.SaveTeachplanDto;
import com.lh.content.model.vo.TeachplanVo;
import com.lh.content.service.TeachPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "课程计划编辑接口",tags = "课程计划编辑接口")
@RestController
public class TeachplanController {
    @Autowired
    private TeachPlanService teachPlanService;



    @ApiOperation("查询课程计划树形结构")
    @ApiImplicitParam(value = "courseId",name = "课程Id",required = true,dataType = "Long",paramType = "path")
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachplanVo> getTeachPlanTreeNodes(@PathVariable Long courseId){
        return teachPlanService.queryTeachPlanTreeByCourseId(courseId);
    }

    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan( @RequestBody SaveTeachplanDto teachplan){
        teachPlanService.saveTeachplan(teachplan);
    }

    @ApiOperation("课程计划删除")
    @DeleteMapping("/teachplan/{teachplanId}")
    public ErrorResult deleteTeachplan(@PathVariable Long teachplanId){
       return teachPlanService.deleteTeachPlanById(teachplanId);
    }

    @ApiOperation("课程计划上移")
    @PostMapping("/teachplan/moveup/{teachplanId}")
    public Result<Object> moveupTeachplan(@PathVariable Long teachplanId){

        return teachPlanService.moveupTeachPlanById(teachplanId);

    }

    @ApiOperation("课程计划下移")
    @PostMapping("/teachplan/movedown/{teachplanId}")
    public Result<Object> movedownTeachplan(@PathVariable Long teachplanId){
        return teachPlanService.movedownTeachPlanById(teachplanId);
    }


    @ApiOperation(value = "课程计划和媒资信息绑定")
    @PostMapping("/teachplan/association/media")
    public void associationMedia(@RequestBody BindTeachplanMediaDto bindTeachplanMediaDto){
        teachPlanService.associationMedia(bindTeachplanMediaDto);
    }





}

