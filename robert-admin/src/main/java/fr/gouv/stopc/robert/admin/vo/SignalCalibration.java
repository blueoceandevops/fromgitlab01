package fr.gouv.stopc.robert.admin.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignalCalibration {

	@JsonProperty("model_name")
	private String modelName;

	private Integer txGain;

	private Integer rxGain;

	@Override
	public String toString() {
		return String.format("{model_name: %s, emissionGain: %d, txGain: %d}", modelName, txGain, rxGain);
	}

}
