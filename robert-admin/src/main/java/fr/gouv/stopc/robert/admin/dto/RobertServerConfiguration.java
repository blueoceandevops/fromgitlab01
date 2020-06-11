package fr.gouv.stopc.robert.admin.dto;

import fr.gouv.stopc.robert.admin.vo.App;
import fr.gouv.stopc.robert.admin.vo.Ble;
import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author plant-stopcovid
 *
 */
@Data
@Builder
public class RobertServerConfiguration {

	private App mobileApp;

	private Ble scoring;
}
