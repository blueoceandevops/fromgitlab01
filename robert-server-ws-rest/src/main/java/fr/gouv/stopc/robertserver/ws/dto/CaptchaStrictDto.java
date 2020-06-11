package fr.gouv.stopc.robertserver.ws.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CaptchaStrictDto {

	@JsonProperty("result")
	private String result;

	@JsonProperty("code")
	private String errorCode;

	@JsonProperty("message")
	private String errorMessage;

}
