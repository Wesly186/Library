package com.library.manage.service;

import java.io.File;

import com.library.common.pojo.KEUploadResult;

public interface PictureService {

    KEUploadResult uploadPicture(String uploadFileName, File upload);

}
