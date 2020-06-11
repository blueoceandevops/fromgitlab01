package fr.gouv.stopc.robertserver.ws.controller.impl;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import fr.gouv.stopc.robert.crypto.grpc.server.client.service.ICryptoServerGrpcClient;
import fr.gouv.stopc.robert.server.common.service.IServerConfigurationService;
import fr.gouv.stopc.robertserver.database.service.IApplicationConfigService;
import fr.gouv.stopc.robertserver.database.service.IRegistrationService;
import fr.gouv.stopc.robertserver.ws.controller.IRegisterStrictController;
import fr.gouv.stopc.robertserver.ws.dto.RegisterResponseDto;
import fr.gouv.stopc.robertserver.ws.exception.RobertServerException;
import fr.gouv.stopc.robertserver.ws.service.CaptchaStrictService;
import fr.gouv.stopc.robertserver.ws.vo.RegisterStrictVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RegisterStrictControllerImpl extends AbstractRegisterControllerImpl implements IRegisterStrictController {

    final CaptchaStrictService captchaStrictService;

    @Inject
    public RegisterStrictControllerImpl(final IRegistrationService registrationService,
            final IServerConfigurationService serverConfigurationService,
            final IApplicationConfigService applicationConfigService,
            final CaptchaStrictService captchaStrictService,
            final ICryptoServerGrpcClient cryptoServerClient) {

        this.registrationService = registrationService;
        this.serverConfigurationService = serverConfigurationService;
        this.applicationConfigService = applicationConfigService;
        this.captchaStrictService = captchaStrictService;
        this.cryptoServerClient = cryptoServerClient;

    }

	@Override
	public ResponseEntity<RegisterResponseDto> register(@Valid RegisterStrictVo registerVo)
			throws RobertServerException {

		if (StringUtils.isEmpty(registerVo.getCaptcha())) {
            log.error("The captcha is required.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (StringUtils.isEmpty(registerVo.getClientPublicECDHKey())) {
            log.error("The client ECDH public key is required.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (!this.captchaStrictService.verifyCaptcha(registerVo)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        return postCheckRegister(registerVo);
	}
}