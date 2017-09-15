package com.library.manage.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.library.common.pojo.KEUploadResult;
import com.library.common.utils.FtpUtils;
import com.library.manage.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService {

    @Value("${FTP_ADDRESS}")
    private String FTP_ADDRESS;
    @Value("${FTP_PORT}")
    private Integer FTP_PORT;
    @Value("${FTP_USERNAME}")
    private String FTP_USERNAME;
    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;
    @Value("${FTP_BASE_PATH}")
    private String FTP_BASE_PATH;
    @Value("${IMAGE_BASE_URL}")
    private String IMAGE_BASE_URL;

    @Override
    public KEUploadResult uploadPicture(String oldName, File upload) {
	KEUploadResult kResult = new KEUploadResult();
	try {
	    // 生成新文件名
	    String newName = UUID.randomUUID().toString();
	    newName = newName + oldName.substring(oldName.lastIndexOf("."));
	    // 图片上传
	    String imagePath = new DateTime().toString("/yyyy/MM/dd");
	    boolean result = FtpUtils.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH,
		    imagePath, newName, new FileInputStream(upload));
	    // 返回结果
	    if (!result) {
		kResult.setError(1);
		kResult.setMessage("文件上传失败");
		return kResult;
	    }
	    kResult.setError(0);
	    kResult.setUrl(IMAGE_BASE_URL + imagePath + "/" + newName);
	    return kResult;

	} catch (Exception e) {
	    kResult.setError(1);
	    kResult.setMessage("文件上传发生异常");
	    return kResult;
	}
    }

}
