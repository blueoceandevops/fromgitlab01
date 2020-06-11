package fr.gouv.stopc.robert.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mobile Application configuration
 * 
 * @author plant-stopcovid
 * @version 0.0.1-SNAPSHOT
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileAppConfiguration {

	/**
	 * Number of calls per day made by the mobile app to check the status
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Integer checkStatusFrequency;

	/**
	 * Hello messages retention duration on the mobile app
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Integer dataRetentionPeriod;

	/**
	 * Contagious period duration before symptoms span
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Integer preSymptomsSpan;

	/**
	 * Beginning hour of the day to start status requests
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Integer minHourContactNotif;

	/**
	 * Ending hour of the day to start status requests
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Integer maxHourContactNotif;

	/**
	 * Availability of the App
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Boolean appAvailability;

	/**
	 * Force upgrade of the mobile App
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Boolean appUpgrade;
}
