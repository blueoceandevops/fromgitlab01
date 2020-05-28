package fr.gouv.stopc.robertserver.ws.service.impl;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;

import fr.gouv.stopc.robertserver.ws.config.ApplicationConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import fr.gouv.stopc.robert.server.common.service.IServerConfigurationService;
import fr.gouv.stopc.robertserver.ws.dto.CaptchaDto;
import fr.gouv.stopc.robertserver.ws.service.CaptchaService;
import fr.gouv.stopc.robertserver.ws.vo.RegisterVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CaptchaServiceImpl implements CaptchaService {

	private static final long CONVERSIONTIMEMS = 1000L;
	private RestTemplate restTemplate;

	private IServerConfigurationService serverConfigurationService;

	private ApplicationConfig applicationConfig;

	@Inject
	public CaptchaServiceImpl(RestTemplate restTemplate, IServerConfigurationService serverConfigurationService, ApplicationConfig applicationConfig) {

		this.restTemplate = restTemplate;
		this.serverConfigurationService = serverConfigurationService;
		this.applicationConfig = applicationConfig;
	}

	@Override
	public boolean verifyCaptcha(final RegisterVo registerVo) {

		// TODO: Remove this forced returned value until reCATPCHA service is really
		// used but method verifyCaptcha
		// must be called for test
		return true || Optional.ofNullable(registerVo).map(item -> {

			HttpEntity<RegisterVo> request = new HttpEntity(new CaptchaVo(item.getCaptcha(), this.applicationConfig.getCaptchaSecret()).toString(), initHttpHeaders());
			Date sendingDate = new Date();

			ResponseEntity<CaptchaDto> response = null;
			try {
				response = this.restTemplate.postForEntity(this.applicationConfig.getCaptchaVerifyUrl(), request, CaptchaDto.class);
			} catch(RestClientException e) {
				log.error(e.getMessage());
				return false;
			}

			return Optional.ofNullable(response).map(ResponseEntity::getBody).filter(captchaDto -> Objects.nonNull(captchaDto.getChallengeTimestamp())).map(captchaDto -> {

				log.info("Result of CAPTCHA verification: {}", captchaDto);
				return isSuccess(captchaDto, sendingDate);
			}).orElse(false);

		}).orElse(false);
	}

	private HttpHeaders initHttpHeaders() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return headers;
	}

	private boolean isSuccess(CaptchaDto captchaDto, Date sendingDate) {

		return this.applicationConfig.getCaptchaHostname().equals(captchaDto.getHostname()) && Math
				.abs(sendingDate.getTime() - captchaDto.getChallengeTimestamp().getTime()) <= this.serverConfigurationService.getCaptchaChallengeTimestampTolerance() * CONVERSIONTIMEMS;
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	class CaptchaVo {

		private String secret;

		private String response;

	}

}
