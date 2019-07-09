package com.rama.data.process.service;

import com.rama.data.process.exception.DataProcessException;
import com.rama.data.process.response.DataDownloadResponse;
import com.rama.data.process.response.DataUploadResponse;



public interface DataProcessService {
	DataDownloadResponse downloadProcess(String filepath, String name) throws DataProcessException;
	DataUploadResponse uploadProcess(String filepath,String name) throws DataProcessException;
}