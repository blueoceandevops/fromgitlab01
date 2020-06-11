package fr.gouv.stopc.robert.admin.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.gouv.stopc.robert.admin.service.IStopCovidConfigurationService;
import fr.gouv.stopc.robert.admin.vo.ConfigurationHistoryEntry;
import fr.gouv.stopc.robert.admin.vo.FunctionalConfiguration;

/**
 * REST controller that allows to :<br>
 * - update the configuration of an application for a given profile<br>
 * - retrieve the modification history of an application for a given profile
 * 
 * @author plant-stopcovid
 * @version 0.0.1-SNAPSHOT
 */
@RestController
@RequestMapping("/api/v1/config")
@CrossOrigin
public class StopCovidConfigurationController {

	/**
	 * The service for configuration manipulations
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private IStopCovidConfigurationService service;

	/**
	 * Spring injection constructor
	 * 
	 * @param service the <code>IRobertConfigurationService</code> bean to use
	 * @since 0.0.1-SNAPSHOT
	 */
	public StopCovidConfigurationController(IStopCovidConfigurationService service) {
		this.service = service;
	}

	/**
	 * Update the functional configuration for a given profile
	 * 
	 * @param appName          the name of the application to update
	 * @param profile          the profile the application is running on
	 * @param newConfiguration the configuration in json format
	 * @return the updated configuration
	 */
	@RolesAllowed("${robert.admin.authorized-roles}")
	@PutMapping(path = "/{profile}")
	public ResponseEntity<fr.gouv.stopc.robert.admin.vo.FunctionalConfiguration> updateConfiguration(
			@PathVariable("profile") String profile, @RequestBody FunctionalConfiguration newConfiguration) {
		return null;
	}

	/**
	 * Retrieve the history of modifications of the functional configuration for a
	 * given Spring profile
	 * 
	 * @param profile the spring profile
	 * @return the history of modifications
	 * @since 0.0.1-SNAPSHOT
	 */
	@RolesAllowed("${robert.admin.authorized-roles}")
	@GetMapping(path = "/history/{profile}")
	public ResponseEntity<List<ConfigurationHistoryEntry>> getFunctionalConfigurationHistory(
			@PathVariable(name = "profile") String profile) {
		return ResponseEntity.ok(this.service.getHistory(profile));
	}

	/**
	 * Retrieve functionnal configuration for a Spring profile
	 * 
	 * @param profile the spring profile
	 * @return the history of modifications
	 * @since 0.0.1-SNAPSHOT
	 */
	@RolesAllowed("${robert.admin.authorized-roles}")
	@GetMapping(path = "/{profile}")
	public ResponseEntity<FunctionalConfiguration> getFunctionalConfiguration(
			@PathVariable(name = "profile") String profile) {
		return ResponseEntity.ok(this.service.getConfiguration(profile));
	}
}
