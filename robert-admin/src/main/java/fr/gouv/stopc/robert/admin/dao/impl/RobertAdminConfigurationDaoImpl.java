package fr.gouv.stopc.robert.admin.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.kv.model.GetValue;

import fr.gouv.stopc.robert.admin.dao.IRobertAdminConfigurationDao;
import fr.gouv.stopc.robert.admin.dto.ComparisonResult;
import fr.gouv.stopc.robert.admin.dto.RobertBatchConfiguration;
import fr.gouv.stopc.robert.admin.dto.RobertServerConfiguration;
import fr.gouv.stopc.robert.admin.utils.FunctionalConfigurationMapper;
import fr.gouv.stopc.robert.admin.utils.RobertAdminMapper;
import fr.gouv.stopc.robert.admin.utils.RobertAdminUtil;
import fr.gouv.stopc.robert.admin.vo.ConfigurationHistoryEntry;
import fr.gouv.stopc.robert.admin.vo.FunctionalConfiguration;

/**
 * 
 * @author plant-stopcovid
 *
 */
@Component
@RefreshScope
public class RobertAdminConfigurationDaoImpl implements IRobertAdminConfigurationDao {

	private ConsulClient client;

	private RobertAdminMapper mapper;

	@Value("${robert.admin.rs-config-prefix}")
	private String rsConfigPrefix;

	@Value("${robert.admin.rb-config-prefix}")
	private String rbConfigPrefix;

	/**
	 * 
	 * @param client
	 */
	public RobertAdminConfigurationDaoImpl(ConsulClient client, RobertAdminMapper mapper) {
		this.client = client;
		this.mapper = mapper;
	}

	@Override
	public List<ConfigurationHistoryEntry> getHistory(String profile) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FunctionalConfiguration getConfiguration(String profile) {
		List<GetValue> robertServerValues = client.getKVValues(rsConfigPrefix).getValue();
		List<GetValue> robertBatchValues = client.getKVValues(rbConfigPrefix).getValue();
		return mapper.toFunctionalConfiguration(robertServerValues, robertBatchValues, rsConfigPrefix, rbConfigPrefix);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FunctionalConfiguration updateConfiguration(String profile, FunctionalConfiguration newConfiguration) {

		// Retrieve the current configuration from Consul
		List<GetValue> currentRsValues = client.getKVValues(rsConfigPrefix).getValue();
		List<GetValue> currentRbValues = client.getKVValues(rbConfigPrefix).getValue();

		// Map Consul config into FunctionalConfiguration
		FunctionalConfiguration currentConfiguration = mapper.toFunctionalConfiguration(currentRsValues,
				currentRbValues, rsConfigPrefix, rbConfigPrefix);

		// Compare the current configuration with the new configuration
		List<ComparisonResult> diffs = RobertAdminUtil.compareObjects(currentConfiguration, newConfiguration);

		// If any changes then update the Consul configuration
		if (CollectionUtils.isEmpty(diffs)) {
			// Convert and update the Robert Server configuration
			RobertServerConfiguration rsConf = FunctionalConfigurationMapper.INSTANCE
					.toRobertServerConfiguration(newConfiguration);
			Map<String, String> newRsValues = mapper.toKeyValue(rsConf, rsConfigPrefix);
			newRsValues.entrySet().stream().forEach(kv -> client.setKVValue(kv.getKey(), kv.getValue()));
			// Convert and update the Robert Batch configuration
			RobertBatchConfiguration rbConf = FunctionalConfigurationMapper.INSTANCE
					.toRobertBatchConfiguration(newConfiguration);
			Map<String, String> newRbValues = mapper.toKeyValue(rbConf, rbConfigPrefix);
			newRbValues.entrySet().stream().forEach(kv -> client.setKVValue(kv.getKey(), kv.getValue()));
		}

		return newConfiguration;
	}
}
