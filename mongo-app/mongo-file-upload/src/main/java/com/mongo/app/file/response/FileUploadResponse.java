package com.mongo.app.file.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonAutoDetect

public class FileUploadResponse {
	List<String> exception;
	int filecount;
}
