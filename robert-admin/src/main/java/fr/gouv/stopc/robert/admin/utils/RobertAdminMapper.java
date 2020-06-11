package fr.gouv.stopc.robert.admin.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import com.ecwid.consul.v1.kv.model.GetValue;

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
public class RobertAdminMapper {

	/**
	 * 
	 * @param rsConsulValues
	 * @param rbConsulValues
	 * @return
	 */
	public FunctionalConfiguration toFunctionalConfiguration(List<GetValue> rsConsulValues,
			List<GetValue> rbConsulValues, String rsPrefix, String rbPrefix) {
		Properties rsConsulProps = new Properties();
		rsConsulValues.stream().forEach(val -> rsConsulProps.put(val.getKey(), val.getKey()));
		Properties rbConsulProps = new Properties();
		rbConsulValues.stream().forEach(val -> rbConsulProps.put(val.getKey(), val.getKey()));

		RobertServerConfiguration rsConf = toObject(RobertServerConfiguration.class, rsConsulProps, rsPrefix);
		RobertBatchConfiguration rbConf = toObject(RobertBatchConfiguration.class, rbConsulProps, rbPrefix);
		return FunctionalConfigurationMapper.INSTANCE.toFunctionalConfiguration(rsConf, rbConf);
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
						// the name of property added to the prefix
						rsKv.putAll(toKeyValue(x.getReadMethod().invoke(toConvert), prefix + "." + x.getName()));
					} else {
						// If not a DTO from Robert Admin then kebab-casify the name & stringify the
						// value
						rsKv.put(prefix + RobertAdminUtil.camelToKebabCase(x.getName()),
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

	/**
	 * 
	 * @param consulValues
	 * @return
	 */
	private <T extends Object> T toObject(Class<T> objectClass, Properties consulValues, String prefix) {
		T result;
		try {
			result = objectClass.newInstance();
			PropertyDescriptor[] propDescArr = Introspector.getBeanInfo(objectClass, Object.class)
					.getPropertyDescriptors();
		} catch (InstantiationException | IllegalAccessException | IntrospectionException e) {
			log.error("Error converting Consul values : {}", e.getMessage());
			result = null;
		}
		return result;
	}
}
