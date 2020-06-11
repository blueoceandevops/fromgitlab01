package fr.gouv.stopc.robert.admin.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ProximityTracing {
	
	private App app;
	
	private Ble ble;
	
	private Float riskThreshold;
	
	private Integer rssi1m;
	
	private Integer mu0;
	
	private Float r0;
}
