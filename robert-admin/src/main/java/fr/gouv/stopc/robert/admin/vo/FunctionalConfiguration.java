package fr.gouv.stopc.robert.admin.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.gouv.stopc.robert.admin.dto.RobertBatchConfiguration;
import fr.gouv.stopc.robert.admin.dto.RobertServerConfiguration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class FunctionalConfiguration {

	/**
	 * 
	 * @param rsConf
	 * @param rbConf
	 */
	public FunctionalConfiguration(RobertServerConfiguration rsConf, RobertBatchConfiguration rbConf) {
		// TODO Auto-generated constructor stub
	}

	private AccountManagement account;

	private ProximityTracing tracing;

}
