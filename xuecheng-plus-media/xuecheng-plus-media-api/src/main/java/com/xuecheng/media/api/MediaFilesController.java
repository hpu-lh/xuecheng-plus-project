package com.xuecheng.media.api;

import com.lh.base.model.PageParams;
import com.lh.base.model.PageResult;
import com.xuecheng.media.model.dto.QueryMediaParamsDto;
import com.xuecheng.media.model.dto.UploadFileparamsDto;
import com.xuecheng.media.model.po.MediaFiles;
import com.xuecheng.media.model.vo.UploadFileResultVo;
import com.xuecheng.media.service.MediaFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @description 媒资文件管理接口
 * @author Mr.M
 * @date 2022/9/6 11:29
 * @version 1.0
 */
 @Api(value = "媒资文件管理接口",tags = "媒资文件管理接口")
 @RestController
public class MediaFilesController {


  @Autowired
  MediaFileService mediaFileService;


 @ApiOperation("媒资列表查询接口")
 @PostMapping("/files")
 public PageResult<MediaFiles> list(PageParams pageParams, @RequestBody QueryMediaParamsDto queryMediaParamsDto){
  Long companyId = 1232141425L;
  return mediaFileService.queryMediaFiels(companyId,pageParams,queryMediaParamsDto);

 }
 @ApiOperation("上传文件")
 @RequestMapping(value = "/upload/coursefile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
 public UploadFileResultVo upload(@RequestParam("filedata") MultipartFile filedata,@RequestParam(value= "objectName",required=false) String objectName) throws IOException {
     UploadFileparamsDto uploadFileparamsDto = new UploadFileparamsDto();
     uploadFileparamsDto.setFilename(filedata.getOriginalFilename());
     Long size=filedata.getSize();
     uploadFileparamsDto.setFileSize(size);
     uploadFileparamsDto.setFileType("001001");

     File tempFile = File.createTempFile("minio", "temp");
     filedata.transferTo(tempFile);

     String localFilePath = tempFile.getAbsolutePath();
     Long companyId = 1232141425L;
     return mediaFileService.uploadFile(companyId,uploadFileparamsDto,localFilePath,objectName);
 }





}
