package test.fr.gouv.stopc.robertserver.ws;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import fr.gouv.stopc.robertserver.ws.RobertServerWsRestApplication;
import fr.gouv.stopc.robertserver.ws.dto.ReportBatchResponseDto;
import fr.gouv.stopc.robertserver.ws.dto.VerifyResponseDto;
import fr.gouv.stopc.robertserver.ws.exception.ApiError;
import fr.gouv.stopc.robertserver.ws.exception.RobertServerException;
import fr.gouv.stopc.robertserver.ws.service.ContactDtoService;
import fr.gouv.stopc.robertserver.ws.utils.MessageConstants;
import fr.gouv.stopc.robertserver.ws.utils.UriConstants;
import fr.gouv.stopc.robertserver.ws.vo.DistinctiveHelloInfoWithinEpochForSameEBIDVo;
import fr.gouv.stopc.robertserver.ws.vo.GroupedHellosReportVo;
import fr.gouv.stopc.robertserver.ws.vo.ReportBatchRequestVo;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { RobertServerWsRestApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application.properties")
public class ReportControllerWsRestTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	private HttpEntity<ReportBatchRequestVo> requestEntity;

	private HttpHeaders headers;

	@MockBean
	private ContactDtoService contactDtoService;

	@MockBean
	private RestTemplate restTemplate;

	@Value("${controller.path.prefix}")
	private String pathPrefix;

	private URI targetUrl;

	private final String token = "23DC4B32-7552-44C1-B98A-DDE5F75B1729";

	private final String contactsAsBinary = "contactsAsBinary";

	private List<GroupedHellosReportVo> contacts;

	private ReportBatchRequestVo reportBatchRequestVo;

	private static final String EXCEPTION_FAIL_MESSAGE = "Should not fail with exception";

	@BeforeEach
	public void setup() {

		this.headers = new HttpHeaders();
		this.headers.setContentType(MediaType.APPLICATION_JSON);

		this.targetUrl = UriComponentsBuilder.fromUriString(this.pathPrefix).path(UriConstants.REPORT).build().encode().toUri();

		DistinctiveHelloInfoWithinEpochForSameEBIDVo info = DistinctiveHelloInfoWithinEpochForSameEBIDVo.builder() //
			.timeCollectedOnDevice(1L) //
			.timeFromHelloMessage(1) //
			.mac("1") //
			.rssiRaw(-20) //
			.rssiCalibrated(20) //
			.build();

		GroupedHellosReportVo contact = GroupedHellosReportVo.builder().ecc("FR").ebid("ABCDEFGH").ids(Arrays.asList(info)).build();

		this.contacts = Arrays.asList(contact);

		this.reportBatchRequestVo = ReportBatchRequestVo.builder().token(this.token).contacts(this.contacts).build();
	}

	@Test
	public void testReportShouldNotAcceptInvalidTokenSizeSmall() {
		this.reportBatchRequestVo.setToken("1");
		try {
			// Given
			this.requestEntity = new HttpEntity<>(this.reportBatchRequestVo, this.headers);

			// When
			ResponseEntity<ApiError> response = this.testRestTemplate.exchange(targetUrl, HttpMethod.POST, this.requestEntity, ApiError.class);

			// Then
			assertNotNull(response);
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
			assertNotNull(response.getBody());
			assertEquals(response.getBody(), buildApiError(MessageConstants.INVALID_DATA.getValue()));

			verify(this.contactDtoService, never()).saveContacts(any());
		} catch (RobertServerException e) {
			fail(EXCEPTION_FAIL_MESSAGE);
		}
	}

	@Test
	public void testReportShouldNotAcceptInvalidTokenSizeLarge() {
		this.reportBatchRequestVo.setToken("23DC4B32-7552-44C1-B98A-DDE5F75B1729" + "1");
		try {
			// Given
			this.requestEntity = new HttpEntity<>(this.reportBatchRequestVo, this.headers);

			// When
			ResponseEntity<ApiError> response = this.testRestTemplate.exchange(targetUrl, HttpMethod.POST, this.requestEntity, ApiError.class);

			// Then
			assertNotNull(response);
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
			assertNotNull(response.getBody());
			assertEquals(response.getBody(), buildApiError(MessageConstants.INVALID_DATA.getValue()));

			verify(this.contactDtoService, never()).saveContacts(any());
		} catch (RobertServerException e) {
			fail(EXCEPTION_FAIL_MESSAGE);
		}
	}

	@Test
	public void testReportShouldNotAcceptInvalidTokenSizeIntermediate() {
		this.reportBatchRequestVo.setToken("23DC4B32-7552-44C1-B98A-DDE5F75B172");
		try {
			// Given
			this.requestEntity = new HttpEntity<>(this.reportBatchRequestVo, this.headers);

			// When
			ResponseEntity<ApiError> response = this.testRestTemplate.exchange(targetUrl, HttpMethod.POST, this.requestEntity, ApiError.class);

			// Then
			assertNotNull(response);
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
			assertNotNull(response.getBody());
			assertEquals(response.getBody(), buildApiError(MessageConstants.INVALID_DATA.getValue()));

			verify(this.contactDtoService, never()).saveContacts(any());
		} catch (RobertServerException e) {
			fail(EXCEPTION_FAIL_MESSAGE);
		}
	}

	@Test
	public void testReportLargePayload() {
		try {
			DistinctiveHelloInfoWithinEpochForSameEBIDVo info = DistinctiveHelloInfoWithinEpochForSameEBIDVo.builder() //
				.timeCollectedOnDevice(3797833665L) //
				.timeFromHelloMessage(22465) //
				.mac("MEjHn3mWfhGNhbAooSiVBbVoNayotrLhMPDI8l3tum0=").rssiRaw(0).rssiCalibrated(0).build();

			GroupedHellosReportVo contact = GroupedHellosReportVo.builder().ecc("2g==").ebid("GTr1XTqVS5g=").ids(Arrays.asList(info)).build();

			this.contacts = Arrays.asList(contact);

			this.reportBatchRequestVo = ReportBatchRequestVo.builder().token("23DC4B32-7552-44C1-B98A-DDE5F75B1729").contacts(this.contacts).build();

			this.requestEntity = new HttpEntity<>(this.reportBatchRequestVo, this.headers);
			
			when(this.restTemplate.getForEntity(any(), any())).thenReturn(ResponseEntity.ok(VerifyResponseDto.builder().valid(true).build()));

			ResponseEntity<ReportBatchResponseDto> response = this.testRestTemplate.exchange(targetUrl, HttpMethod.POST, this.requestEntity, ReportBatchResponseDto.class);

			// Then
			assertNotNull(response);
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertNotNull(response.getHeaders());
			assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
			assertNotNull(response.getBody());
			assertThat(response.getBody(), equalTo(buildReportBatchResponseDto()));
			verify(this.contactDtoService, atLeast(1)).saveContacts(any());
		} catch (RobertServerException e) {
			fail(EXCEPTION_FAIL_MESSAGE);
		}
	}

	@Test
	public void testReportContactHistorySucceeds() {

		try {
			// Given
			this.requestEntity = new HttpEntity<>(this.reportBatchRequestVo, this.headers);

			when(this.restTemplate.getForEntity(any(), any())).thenReturn(ResponseEntity.ok(VerifyResponseDto.builder().valid(true).build()));

			// When
			ResponseEntity<ReportBatchResponseDto> response = this.testRestTemplate.exchange(targetUrl, HttpMethod.POST, this.requestEntity, ReportBatchResponseDto.class);

			// Then
			assertNotNull(response);
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertNotNull(response.getHeaders());
			assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
			assertNotNull(response.getBody());
			assertThat(response.getBody(), equalTo(buildReportBatchResponseDto()));

			verify(this.contactDtoService, atLeast(1)).saveContacts(any());
		} catch (RobertServerException e) {
			fail(EXCEPTION_FAIL_MESSAGE);
		}
	}

	@Test
	public void testReportContacWhenContactsProvidedTwice() {

		try {
			// Given
			this.reportBatchRequestVo = ReportBatchRequestVo.builder().token(this.token).contacts(this.contacts).contactsAsBinary(this.contactsAsBinary).build();

			this.requestEntity = new HttpEntity<>(this.reportBatchRequestVo, this.headers);

			// When
			ResponseEntity<ReportBatchResponseDto> response = this.testRestTemplate.exchange(targetUrl, HttpMethod.POST, this.requestEntity, ReportBatchResponseDto.class);

			// Then
			assertNotNull(response);
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

			verify(this.contactDtoService, never()).saveContacts(any());
		} catch (RobertServerException e) {
			fail(EXCEPTION_FAIL_MESSAGE);
		}
	}

	@Test
	public void testReportContacWhenContactsNotProvided() {

		try {
			// Given
			this.reportBatchRequestVo = ReportBatchRequestVo.builder().token(this.token).build();

			this.requestEntity = new HttpEntity<>(this.reportBatchRequestVo, this.headers);

			// When
			ResponseEntity<ReportBatchResponseDto> response = this.testRestTemplate.exchange(targetUrl, HttpMethod.POST, this.requestEntity, ReportBatchResponseDto.class);

			// Then
			assertNotNull(response);
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

			verify(this.contactDtoService, never()).saveContacts(any());
		} catch (RobertServerException e) {
			fail(EXCEPTION_FAIL_MESSAGE);
		}
	}

	@Test
	public void testReportWhenTokenNotProvided() {

		try {
			// Given
			this.reportBatchRequestVo = ReportBatchRequestVo.builder().contacts(this.contacts).build();

			this.requestEntity = new HttpEntity<>(this.reportBatchRequestVo, this.headers);

			// When
			ResponseEntity<ReportBatchResponseDto> response = this.testRestTemplate.exchange(targetUrl, HttpMethod.POST, this.requestEntity, ReportBatchResponseDto.class);

			// Then
			assertNotNull(response);
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

			verify(this.contactDtoService, never()).saveContacts(any());
		} catch (RobertServerException e) {

			fail("Should not fail");
		}
	}

	@Test
	public void testReportContactHistoryWhenUsingGetMethod() throws Exception {

		// Given
		this.requestEntity = new HttpEntity<>(this.reportBatchRequestVo, this.headers);

		// When
		ResponseEntity<String> response = this.testRestTemplate.exchange(targetUrl, HttpMethod.GET, this.requestEntity, String.class);

		// Then
		assertNotNull(response);
		assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
		assertNotNull(response.getBody());
		verify(this.contactDtoService, never()).saveContacts(any());
	}

	@Test
	public void testReportContactHistoryWhenErrorOccurs() throws Exception {

		// Given
		this.requestEntity = new HttpEntity<>(this.reportBatchRequestVo, this.headers);
		
		when(this.restTemplate.getForEntity(any(), any())).thenReturn(ResponseEntity.ok(VerifyResponseDto.builder().valid(true).build()));

		doThrow(new RobertServerException(MessageConstants.ERROR_OCCURED)).when(this.contactDtoService).saveContacts(any());

		// When
		ResponseEntity<ApiError> response = this.testRestTemplate.exchange(targetUrl, HttpMethod.POST, this.requestEntity, ApiError.class);

		// Then
		assertNotNull(response);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertNotNull(response.getHeaders());
		assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
		assertNotNull(response.getBody());
		assertThat(response.getBody(), equalTo(buildApiError(MessageConstants.ERROR_OCCURED.getValue())));

	}

	private ReportBatchResponseDto buildReportBatchResponseDto() {

		return ReportBatchResponseDto.builder().message(MessageConstants.SUCCESSFUL_OPERATION.getValue()).success(true).build();
	}

	private ApiError buildApiError(String message) {

		return ApiError.builder().message(message).build();
	}

}
