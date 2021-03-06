package fr.gouv.stopc.robert.server.batch.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class PropertyLoader {

	@Value("${robert.crypto.server.host}")
	private String cryptoServerHost;

	@Value("${robert.crypto.server.port}")
	private String cryptoServerPort;

	@Value("${robert.protocol.scoring-algo-rssi}")
	private Integer rssiScoringAlgorithm;

	@Value("${robert.protocol.risk-threshold}")
	private Double riskThreshold;

	@Value("${robert.scoring.scoring-algo-r0}")
	private Double r0ScoringAlgorithm;

	@Value("${robert.scoring.batch-mode}")
	private String batchMode;

}
