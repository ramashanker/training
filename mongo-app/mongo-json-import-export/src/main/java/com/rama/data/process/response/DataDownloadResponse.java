package com.rama.data.process.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataDownloadResponse {
	private Map<Object,Object> fileEntry;
	private int collectionCount;
}
