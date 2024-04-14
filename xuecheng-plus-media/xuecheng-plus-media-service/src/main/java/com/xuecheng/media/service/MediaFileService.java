package com.xuecheng.media.service;

import com.lh.base.model.PageParams;
import com.lh.base.model.PageResult;
import com.lh.base.model.RestResponse;
import com.xuecheng.media.model.dto.QueryMediaParamsDto;
import com.xuecheng.media.model.dto.UploadFileparamsDto;
import com.xuecheng.media.model.po.MediaFiles;
import com.xuecheng.media.model.vo.UploadFileResultVo;

import java.io.File;

public interface MediaFileService {

 public PageResult<MediaFiles> queryMediaFiels(Long companyId,PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);


 public UploadFileResultVo uploadFile(Long companyId, UploadFileparamsDto uploadFileParamsDto, String localFilePath);

 public MediaFiles addMediaFilesToDb(Long companyId,String fileMd5,UploadFileparamsDto uploadFileParamsDto,String bucket,String objectName);

 public RestResponse<Boolean> checkFile(String fileMd5);


 public RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex);

 public RestResponse uploadChunk(String fileMd5,int chunk,String localChunkFilePath);


 public RestResponse mergechunks(Long companyId,String fileMd5,int chunkTotal,UploadFileparamsDto uploadFileParamsDto);
 public File downloadFileFromMinIO(String bucket, String objectName);

 public boolean addMediaFilesToMinIO(String localFilePath, String mimeType, String bucket, String objectName);


 public MediaFiles getFileById(String mediaId);
 public UploadFileResultVo uploadFile(Long companyId, UploadFileparamsDto uploadFileParamsDto, String localFilePath,String objectName);

}
