package fr.gouv.stopc.robertserver.ws.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UnregisterResponseDto {

	@NotNull
	private Boolean success;

	@NotBlank
	private String message;

}
