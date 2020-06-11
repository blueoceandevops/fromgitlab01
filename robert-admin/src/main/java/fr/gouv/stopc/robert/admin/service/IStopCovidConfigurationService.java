package fr.gouv.stopc.robert.admin.service;

import java.util.List;

import fr.gouv.stopc.robert.admin.vo.ConfigurationHistoryEntry;
import fr.gouv.stopc.robert.admin.vo.FunctionalConfiguration;

/**
 * 
 * @author plant-stopcovid
 * @version 0.0.1-SNAPSHOT
 */
public interface IStopCovidConfigurationService {

	/**
	 * Function retrieving the configuration history entries for a given spring
	 * profile
	 * 
	 * @param appName the name of the application
	 * @param profile the spring profile name
	 * @return the history entries
	 * @since 0.0.1-SNAPSHOT
	 */
	List<ConfigurationHistoryEntry> getHistory(String profile);

	/**
	 * Function retrieving the configuration history entries for a given spring
	 * profile
	 * 
	 * @param appName the name of the application
	 * @param profile the spring profile name
	 * @return the history entries
	 * @since 0.0.1-SNAPSHOT
	 */
	FunctionalConfiguration getConfiguration(String profile);
	
	/**
	 * Function updating the functional configuration for a given profile
	 * 
	 * @param profile       the spring profile name
	 * @param configuration the new configuration to take into account
	 * @return a message telling what is the result of the update
	 * @since 0.0.1-SNAPSHOT
	 */
	FunctionalConfiguration updateConfiguration(String profile, FunctionalConfiguration configuration);
}
