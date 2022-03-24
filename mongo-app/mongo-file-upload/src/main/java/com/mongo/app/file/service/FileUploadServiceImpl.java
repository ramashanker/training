package com.mongo.app.file.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongo.app.file.exception.ProcessException;
import com.mongo.app.file.response.FileUploadResponse;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	private MongoTemplate mongoTemplate;

	public FileUploadServiceImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public FileUploadResponse uploadFileProcess(MultipartFile[] multipartFiles, String name) throws ProcessException {
		FileUploadResponse uploadFileResponse = new FileUploadResponse();
		List<String> exception = new ArrayList<>();
		int counter = 0;
		List<Document> documents = new ArrayList<>();
		for (MultipartFile multipartFile : multipartFiles) {
			String fileName = multipartFile.getOriginalFilename();
			String fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);
			InputStream inputStream;
			try {
				inputStream = multipartFile.getInputStream();
				String jsonTxt = IOUtils.toString(inputStream, "UTF-8");
				Document doc = Document.parse(jsonTxt);
				documents.add(doc);
				mongoTemplate.insert(documents, fileNameWithoutExtension);
			} catch (UnsupportedEncodingException ex) {
				exception.add(ex.getMessage());
				ex.printStackTrace();
			} catch (IOException ex) {
				exception.add(ex.getMessage());
				ex.printStackTrace();
			}
			counter++;

		}
		uploadFileResponse.setFilecount(counter);
		uploadFileResponse.setException(exception);
		return uploadFileResponse;
	}
}
