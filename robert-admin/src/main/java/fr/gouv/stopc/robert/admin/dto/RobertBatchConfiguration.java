package fr.gouv.stopc.robert.admin.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author plant-stopcovid
 *
 */
@Data
@Builder
public class RobertBatchConfiguration {

	private ScoringAlgorithmConfiguration scoring;
	
}
