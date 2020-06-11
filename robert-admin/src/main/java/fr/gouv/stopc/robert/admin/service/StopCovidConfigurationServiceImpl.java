package fr.gouv.stopc.robert.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.gouv.stopc.robert.admin.dao.IStopCovidAdminConfigurationDao;
import fr.gouv.stopc.robert.admin.vo.ConfigurationHistoryEntry;
import fr.gouv.stopc.robert.admin.vo.FunctionalConfiguration;

@Service
public class StopCovidConfigurationServiceImpl implements IStopCovidConfigurationService {

	private IStopCovidAdminConfigurationDao dao;

	public StopCovidConfigurationServiceImpl(IStopCovidAdminConfigurationDao dao) {
		this.dao = dao;
	}

	@Override
	public List<ConfigurationHistoryEntry> getHistory(String profile) {
		return dao.getHistory(profile);
	}

	@Override
	public FunctionalConfiguration getConfiguration(String profile) {
		return dao.getConfiguration(profile);
	}

	@Override
	public FunctionalConfiguration updateConfiguration(String profile, FunctionalConfiguration configuration) {
		return dao.updateConfiguration(profile, configuration);
	}

}
