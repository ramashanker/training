package com.rama.data.process.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RestResponse {
	  @ApiModelProperty(value = "HTTP Status code", example = "200")
	    private final int responseCode;

	    @ApiModelProperty(value = "Description of the response", example = "<response message>")
	    private final String responseMessage;
}
