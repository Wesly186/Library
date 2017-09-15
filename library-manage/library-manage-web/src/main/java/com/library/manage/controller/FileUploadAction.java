package com.library.manage.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.library.common.pojo.KEUploadResult;
import com.library.manage.service.PictureService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
@SuppressWarnings("serial")
public class FileUploadAction extends ActionSupport {

    private File upload;
    private String uploadContentType;
    private String uploadFileName;

    @Autowired
    private PictureService pictureService;
    private KEUploadResult result;

    public String pictureUpload() {
	result = pictureService.uploadPicture(uploadFileName,upload);
	return SUCCESS;
    }

    public KEUploadResult getResult() {
	return result;
    }

    public void setResult(KEUploadResult result) {
	this.result = result;
    }

    public File getUpload() {
	return upload;
    }

    public void setUpload(File upload) {
	this.upload = upload;
    }

    public String getUploadContentType() {
	return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
	this.uploadContentType = uploadContentType;
    }

    public String getUploadFileName() {
	return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
	this.uploadFileName = uploadFileName;
    }
}
