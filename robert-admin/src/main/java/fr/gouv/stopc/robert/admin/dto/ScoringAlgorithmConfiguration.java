package fr.gouv.stopc.robert.admin.dto;

import java.util.ArrayList;
import java.util.List;

import fr.gouv.stopc.robert.admin.vo.SignalCalibration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author plant-stopcovid
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoringAlgorithmConfiguration {

	/**
	 * Maximum number of contacts by epoch
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Integer simultaneousContacts;

	/**
	 * Receiving & Transmitting gain to apply for each phone model
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private List<SignalCalibration> signalCalibrationPerModel = new ArrayList<>();

	/**
	 * Window duration
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Integer tWin;

	/**
	 * Overlap between two sliding windows
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Integer tOverlap;

	/**
	 * Weight for measures depending on received packet in a window
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private List<Integer> delta = new ArrayList<>();

	/**
	 * Limit power of the BLE signal
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Integer p0;

	/**
	 * Constant used to average the risk
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Integer b;

	/**
	 * Max number of message per window per contact per epoch
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Integer maxSampleSize;

	/**
	 * Threshold under which the risk is low
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Float riskThresholdLow;

	/**
	 * Threshold beyond which the risk is high
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Float riskThresholdHigh;

	/**
	 * Minimum score of risk
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Integer riskMin;

	/**
	 * Maximum score of risk
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Integer riskMax;

	/**
	 * Minimum duration to take into account the contact
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Integer dThreshold;

	/**
	 * Threshold for the RSSI
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Integer rssiThreshold;

	/**
	 * Flag indicating if peaks need to be deleted
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Boolean tagPeak;

	/**
	 * Flag indicating if RSSI values needs to be corrected
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private Boolean flagCalib;

	/**
	 * Way the informations are provided
	 * 
	 * @since 0.0.1-SNAPSHOT
	 */
	private String flagMode;
	
}
