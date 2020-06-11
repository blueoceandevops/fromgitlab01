package test.fr.gouv.stopc.robertserver.ws.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import fr.gouv.stopc.robert.server.common.service.IServerConfigurationService;
import fr.gouv.stopc.robertserver.ws.dto.CaptchaStrictDto;
import fr.gouv.stopc.robertserver.ws.service.impl.CaptchaStrictServiceImpl;
import fr.gouv.stopc.robertserver.ws.utils.PropertyLoader;
import fr.gouv.stopc.robertserver.ws.vo.RegisterStrictVo;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application.properties")
public class CaptchaStrictServiceImplTest {

    @Value("${captcha.strict.verify.url}")
    private String captchaStrictVerificationUrl;

    @Value("${captcha.strict.success.code}")
    private String captchaStrictSuccessCode;

    @InjectMocks
    private CaptchaStrictServiceImpl captchaStrictServiceImpl;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private IServerConfigurationService serverConfigurationService;

    @Mock
    private PropertyLoader propertyLoader;

    private RegisterStrictVo registerVo;

    @BeforeEach
    public void beforeEach() {

        this.registerVo = RegisterStrictVo.builder().captcha("captcha").captchaId("captchaId").build();
    }

    @Test
    public void testVerifyCaptchaWhenVoIsNull() {

        // When
        boolean isVerified = this.captchaStrictServiceImpl.verifyCaptcha(null);

        // Then
        assertFalse(isVerified);
    }

    @Test
    public void testVerifyCaptchaWhenVoHasNoCaptcha() {

        // Given
        this.registerVo.setCaptcha(null);

        // When
        boolean isVerified = this.captchaStrictServiceImpl.verifyCaptcha(null);

        // Then
        assertFalse(isVerified);
    }

    @Test
    public void testVerifyCaptchaWhenVoIsNotNull() {

        // Given
        CaptchaStrictDto captchaDto = CaptchaStrictDto.builder()
                .result("SUCCESS")
                .errorCode(null)
                .errorMessage(null)
                .build();
        when(this.restTemplate.postForEntity(any(URI.class), any(),
                any())).thenReturn(ResponseEntity.ok(captchaDto));

        when(this.propertyLoader.getCaptchaStrictVerificationUrl()).thenReturn(this.captchaStrictVerificationUrl);
        when(this.propertyLoader.getCaptchaStrictSuccessCode()).thenReturn(this.captchaStrictSuccessCode);

        // When
        boolean isVerified = this.captchaStrictServiceImpl.verifyCaptcha(this.registerVo);

        // Then
        assertTrue(isVerified);
    }

    @Test
    public void testVerifyCaptchaWhenErrorIsThrown() {

        // Given
        when(this.propertyLoader.getCaptchaStrictVerificationUrl()).thenReturn(this.captchaStrictVerificationUrl);
        when(this.propertyLoader.getCaptchaStrictSuccessCode()).thenReturn(this.captchaStrictSuccessCode);
        when(this.restTemplate.postForEntity(any(String.class), any(), any())).thenThrow(RestClientException.class);

        // When
        boolean isVerified = this.captchaStrictServiceImpl.verifyCaptcha(this.registerVo);

        // Then
        assertFalse(isVerified);
    }

}
