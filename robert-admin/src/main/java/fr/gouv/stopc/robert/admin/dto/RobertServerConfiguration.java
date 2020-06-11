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
public class RobertServerConfiguration {

	private Integer appAutonomy;

	private Integer maxSimultaneousRegister;

	private MobileAppConfiguration app;

	private ScoringAlgorithmConfiguration scoring;
}
