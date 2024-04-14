package com.lh.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.content.model.po.TeachplanMedia;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author itcast
 */
public interface TeachplanMediaMapper extends BaseMapper<TeachplanMedia> {

    public void deleteTeachplanMediaByTeachplanId(Long teachplanId);

}
