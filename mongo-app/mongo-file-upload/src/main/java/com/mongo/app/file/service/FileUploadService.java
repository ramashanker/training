package com.mongo.app.file.service;

import org.springframework.web.multipart.MultipartFile;

import com.mongo.app.file.exception.ProcessException;
import com.mongo.app.file.response.FileUploadResponse;

public interface FileUploadService {
	FileUploadResponse uploadFileProcess(MultipartFile[] multipartFile, String name) throws ProcessException;

}
