package fr.gouv.stopc.robertserver.ws.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Getter;

@RefreshScope
@Getter
@Component
public class PropertyLoader {

	@Value("${robert.crypto.hostname}")
	private String cryptoServerHost;

	@Value("${robert.crypto.port}")
	private String cryptoServerPort;

	/**
	 * 
	 * @return the verification URL for the captcha
	 */
	@Value("${captcha.verify-url}")
	private String captchaVerificationUrl;

	/**
	 * 
	 * @return the secret to be sent to the captcha server along with challenge response
	 */
	@Value("${captcha.secret}")
	private String captchaSecret;

	/**
	 * 
	 * @return the hostname of the site to check against the response from the captcha server
	 */
	@Value("${captcha.hostname}")
	private String captchaHostname;

}
