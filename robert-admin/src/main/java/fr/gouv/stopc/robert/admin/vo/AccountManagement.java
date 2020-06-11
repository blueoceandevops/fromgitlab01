package fr.gouv.stopc.robert.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountManagement {

	private Integer appAutonomy;
	
	private Integer maxSimultaneousRegister;
	
}
