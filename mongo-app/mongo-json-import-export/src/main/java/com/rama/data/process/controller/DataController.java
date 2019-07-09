package com.rama.data.process.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rama.data.process.exception.DataProcessException;
import com.rama.data.process.response.DataDownloadResponse;
import com.rama.data.process.response.DataUploadResponse;
import com.rama.data.process.response.RestResponse;
import com.rama.data.process.service.DataProcessService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@EnableAutoConfiguration
public class DataController {

	private final DataProcessService dataUploadService;

	public DataController(final DataProcessService dataUploadService) {
		this.dataUploadService = dataUploadService;
	}

	@ApiOperation(value = "Export data from mongo", response = RestResponse.class)
	@RequestMapping(value = "exportdata", method = GET,  produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<DataDownloadResponse> exportJsonData(
			@ApiParam(name = "dbname", value = "DB name where json files to be exported", required = true) @RequestParam(value = "file", required = false) String filepath,
			@RequestParam(value = "name", required = false) String name) throws IOException {

		if (filepath == null) {
			throw new DataProcessException("File path not provided");
		}
		if (name == null) {
			throw new DataProcessException("database name not provided");
		}
		DataDownloadResponse response = dataUploadService.downloadProcess(filepath, name);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "import json data to mongodb", response = RestResponse.class)
	@RequestMapping(value = "importdata", method = POST,  produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<DataUploadResponse> importJsonData(
			@ApiParam(name = "dbname", value = "DB name where json files to be exported", required = true) @RequestParam(value = "path", required = false) String filepath,
			@RequestParam(value = "name", required = false) String name) throws IOException {
		if (filepath == null) {
			throw new DataProcessException("File path not provided");
		}
		if (name == null) {
			throw new DataProcessException("database name not provided");
		}
		DataUploadResponse response = dataUploadService.uploadProcess(filepath, name);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
