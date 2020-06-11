package fr.gouv.stopc.robert.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author plant-stopcovid
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RobertBatchConfiguration {

	private ScoringAlgorithmConfiguration scoring;
	
}
