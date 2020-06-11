package fr.gouv.stopc.robert.admin.dto;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdConverter;

import fr.gouv.stopc.robert.admin.vo.SignalCalibration;

public class CustomSignalCalibrationConverter extends StdConverter<String, List<SignalCalibration>> {

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public List<SignalCalibration> convert(String value) {
		try {
			return mapper.readValue(value, new TypeReference<List<SignalCalibration>>() {
			});
		} catch (JsonProcessingException e) {
			return null;
		}
	}

}
