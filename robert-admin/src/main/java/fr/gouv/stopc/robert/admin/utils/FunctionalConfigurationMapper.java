package fr.gouv.stopc.robert.admin.utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import fr.gouv.stopc.robert.admin.dto.RobertBatchConfiguration;
import fr.gouv.stopc.robert.admin.dto.RobertServerConfiguration;
import fr.gouv.stopc.robert.admin.vo.FunctionalConfiguration;

/**
 * 
 * @author plant-stopcovid
 *
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FunctionalConfigurationMapper {

	FunctionalConfigurationMapper INSTANCE = Mappers.getMapper(FunctionalConfigurationMapper.class);

	@Mapping(source = "accountManagement.appAutonomy", target = "appAutonomy")
	RobertServerConfiguration toRobertServerConfiguration(FunctionalConfiguration conf);
	
	@Mapping(source = "tracing.ble.simultaneousContacts", target = "scoring.simultaneousContacts")
	RobertBatchConfiguration toRobertBatchConfiguration(FunctionalConfiguration conf);
	
	@Mapping(source = "robertServerConfiguration.appAutonomy", target = "account.appAutonomy")
	FunctionalConfiguration toFunctionalConfiguration(RobertServerConfiguration rsConf, RobertBatchConfiguration rbConf);
	
}
