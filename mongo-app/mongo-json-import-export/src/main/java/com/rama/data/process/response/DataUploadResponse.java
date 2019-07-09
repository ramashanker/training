package com.rama.data.process.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataUploadResponse {
	private Map<String, String> filewithextension;
	private int filecount;
	private List<String> exceptionMessage;
}
