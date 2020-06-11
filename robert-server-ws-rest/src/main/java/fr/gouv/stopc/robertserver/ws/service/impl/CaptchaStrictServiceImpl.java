package fr.gouv.stopc.robertserver.ws.service.impl;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.gouv.stopc.robertserver.ws.dto.CaptchaStrictDto;
import fr.gouv.stopc.robertserver.ws.service.CaptchaStrictService;
import fr.gouv.stopc.robertserver.ws.utils.PropertyLoader;
import fr.gouv.stopc.robertserver.ws.vo.RegisterStrictVo;
import fr.gouv.stopc.robertserver.ws.vo.RegisterVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CaptchaStrictServiceImpl implements CaptchaStrictService {

	private RestTemplate restTemplate;

	private PropertyLoader propertyLoader;

	@Inject
	public CaptchaStrictServiceImpl(RestTemplate restTemplate,
							  PropertyLoader propertyLoader) {

		this.restTemplate = restTemplate;
		this.propertyLoader = propertyLoader;
	}

	@AllArgsConstructor
	@Data
	protected class CaptchaStrictServiceDto{
		 @NotNull
		  @ToString.Exclude
		  private String answer;
	}
	
	@Override
	public boolean verifyCaptcha(final RegisterStrictVo registerVo) {

		return Optional.ofNullable(registerVo).map(captcha -> {

			HttpEntity<CaptchaStrictServiceDto> request = new HttpEntity<CaptchaStrictServiceDto>(
					new CaptchaStrictServiceDto(captcha.getCaptcha()), initHttpHeaders());

			ResponseEntity<CaptchaStrictDto> response = null;
			try {
				response = this.restTemplate.postForEntity(constructUri(captcha.getCaptchaId()), request, CaptchaStrictDto.class);
			} catch (RestClientException e) {
				log.error(e.getMessage());
				return false;
			}

			return Optional.ofNullable(response)
						   .map(ResponseEntity::getBody)
						   .filter(captchaDto -> Objects.nonNull(captchaDto.getResult()) && Objects.equals(captchaDto.getResult(),this.propertyLoader.getCaptchaStrictSuccessCode()))
						   .map(captchaDto -> {
							   return true;
						   })
						   .orElse(false);

		}).orElse(false);
	}

	private HttpHeaders initHttpHeaders() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return headers;
	}

	
	private URI constructUri(String captchaId) {
		HashMap<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("captchaId", captchaId);
	    return UriComponentsBuilder.fromHttpUrl(this.propertyLoader.getCaptchaStrictVerificationUrl())
                .build(uriVariables);
	}

}
