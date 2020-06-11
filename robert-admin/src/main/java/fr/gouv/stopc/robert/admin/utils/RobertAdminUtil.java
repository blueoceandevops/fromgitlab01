package fr.gouv.stopc.robert.admin.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.text.CaseUtils;

import fr.gouv.stopc.robert.admin.dto.ComparisonResult;
import fr.gouv.stopc.robert.admin.vo.FunctionalConfiguration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RobertAdminUtil {

	/**
	 * 
	 * @param currentConf
	 * @param newConf
	 * @return
	 */
	public static List<ComparisonResult> compareObjects(FunctionalConfiguration currentConf, FunctionalConfiguration newConf) {
		List<ComparisonResult> result = new ArrayList<>();
		try {
			PropertyDescriptor[] propDescArr = Introspector.getBeanInfo(FunctionalConfiguration.class, Object.class)
					.getPropertyDescriptors();
			Arrays.stream(propDescArr).forEach(x -> {
				try {
					Object currentValue = x.getReadMethod().invoke(currentConf);
					Object newValue = x.getReadMethod().invoke(newConf);

					if (!Objects.equals(currentValue, newValue)) {
						result.add(ComparisonResult.builder().currentValue(String.valueOf(currentValue))
								.newValue(String.valueOf(newValue)).key(x.getName()).build());
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					log.error("Error comparing configurations : {}", e.getMessage());
				}
			});
		} catch (IntrospectionException e) {
			log.error("Error comparing configurations : {}", e.getMessage());
		}
		return result;
	}

	public static String camelToKebabCase(String str) {
	    return str.replaceAll("([a-z0-9])([A-Z])", "$1-$2").toLowerCase();
	}
	
	public static String kebabToCamelCase(String str) {
		return CaseUtils.toCamelCase(str, false, '-');
	}
}
