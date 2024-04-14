package com.lh.content.service.impl;

import com.lh.content.service.CourseCategoryService;
import com.lh.content.mapper.CourseCategoryMapper;
import com.lh.content.model.vo.CourseCategoryTreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;
    @Override
    public List<CourseCategoryTreeVo> queryCourseCategoryTree(String id) {
        List<CourseCategoryTreeVo> courseCategoryTreeVos = courseCategoryMapper.queryCourseCategoryTree(id);
        List<CourseCategoryTreeVo> result=new ArrayList<>();;
        Map<String, CourseCategoryTreeVo> map = courseCategoryTreeVos.stream().filter(e -> !e.getId().equals(id)).collect(Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));
        courseCategoryTreeVos.stream().filter(e->!id.equals(e.getId())).forEach(item->{
            if(item.getParentid().equals(id)){
                result.add(item);
            }
            CourseCategoryTreeVo parent = map.get(item.getParentid());
            if(parent!=null){
                if(parent.getChildrenTreeNodes()==null){
                    parent.setChildrenTreeNodes(new ArrayList<>());
                }
                parent.getChildrenTreeNodes().add(item);
            }
        });
        return result;
    }
}
