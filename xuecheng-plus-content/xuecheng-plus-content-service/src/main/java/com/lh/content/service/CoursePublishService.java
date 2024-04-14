package com.lh.content.service;

import com.lh.content.model.vo.CoursePreviewVo;

import java.io.File;

public interface CoursePublishService {
    public CoursePreviewVo getCoursePreviewInfo(Long courseId);

    public void commitAudit(Long companyId,Long courseId);

    public void publish(Long companyId,Long courseId);

    public File generateCourseHtml(Long courseId);

    public void  uploadCourseHtml(Long courseId,File file);

}
