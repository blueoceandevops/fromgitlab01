package fr.gouv.stopc.robert.admin.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigurationHistoryEntry {

	private String message;
	
	private LocalDateTime date;
	
}
