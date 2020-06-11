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

	@Mapping(source = "", target = "")
	RobertServerConfiguration toRobertServerConfiguration(FunctionalConfiguration conf);
	
	@Mapping(source = "", target = "")
	RobertBatchConfiguration toRobertBatchConfiguration(FunctionalConfiguration conf);
	
	@Mapping(source = "", target = "")
	FunctionalConfiguration toFunctionalConfiguration(RobertServerConfiguration rsConf, RobertBatchConfiguration rbConf);
	
}
