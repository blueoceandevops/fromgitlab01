package fr.gouv.stopc.robert.admin.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import com.ecwid.consul.v1.kv.model.GetBinaryValue;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;

import fr.gouv.stopc.robert.admin.dto.RobertBatchConfiguration;
import fr.gouv.stopc.robert.admin.dto.RobertServerConfiguration;
import fr.gouv.stopc.robert.admin.vo.FunctionalConfiguration;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author plant-stopcovid
 *
 */
@Slf4j
@Component
@RefreshScope
public class StopCovidAdminMapper {

	private JavaPropsMapper propertiesMapper = new JavaPropsMapper();

	/**
	 * 
	 * @param rsConsulValues
	 * @param rbConsulValues
	 * @return
	 */
	public FunctionalConfiguration toFunctionalConfiguration(List<GetBinaryValue> rsConsulValues,
			List<GetBinaryValue> rbConsulValues, String rsPrefix, String rbPrefix) {
		try {
			// Creation of the corresponding java Properties to use them with jackons
			// JavaPropsMapper
			Properties rsConsulProps = new Properties();
			// Remove the prefix and kebab to camel casify keys
			rsConsulValues.stream().forEach(val -> rsConsulProps
					.put(StopCovidAdminUtil.kebabToCamelCase(StringUtils.remove(val.getKey(), rsPrefix)).replace("/", "."), new String(val.getValue())));
			Properties rbConsulProps = new Properties();
			rbConsulValues.stream().forEach(val -> rbConsulProps
					.put(StopCovidAdminUtil.kebabToCamelCase(StringUtils.remove(val.getKey(), rbPrefix)).replace("/", "."), new String(val.getValue())));
			// Convert java Properties to object
			RobertServerConfiguration rsConf = propertiesMapper.readPropertiesAs(rsConsulProps,
					RobertServerConfiguration.class);
			RobertBatchConfiguration rbConf = propertiesMapper.readPropertiesAs(rbConsulProps,
					RobertBatchConfiguration.class);
			return FunctionalConfigurationMapper.INSTANCE.toFunctionalConfiguration(rsConf, rbConf);
		} catch (IOException e) {
			log.error("Error converting Consul configurations : {}", e.getMessage());
			return null;
		}
	}

	/**
	 * 
	 * @param <T>
	 * @param toConvert
	 * @param prefix
	 * @return
	 */
	public <T extends Object> Map<String, String> toKeyValue(T toConvert, String prefix) {
		Map<String, String> rsKv = new HashMap<String, String>();
		try {
			// Iterate over the properties of RobertServerConfiguration.
			// Robert Server configuration key name = rbConfigPrefix + <prop name>
			PropertyDescriptor[] propDescArr = Introspector.getBeanInfo(toConvert.getClass(), Object.class)
					.getPropertyDescriptors();
			Arrays.stream(propDescArr).forEach(x -> {
				try {
					if (x.getPropertyType().getPackage().getName().equals("fr.gouv.stopc.robert.admin.dto")) {
						// If the current property is a DTO of Robert Admin then convert into kv with
						// the name of property added to the prefix. Consul property path separator is
						// '/' and not '.'
						rsKv.putAll(toKeyValue(x.getReadMethod().invoke(toConvert), prefix + "/" + x.getName()));
					} else {
						// If not a DTO from Robert Admin then kebab-casify the name & stringify the
						// value
						rsKv.put(prefix + StopCovidAdminUtil.camelToKebabCase(x.getName()),
								x.getReadMethod().invoke(toConvert).toString());
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					log.error("Error comparing configurations : {}", e.getMessage());
				}
			});
		} catch (IntrospectionException e) {
			log.error("Error comparing configurations : {}", e.getMessage());
		}
		return rsKv;
	}

}
