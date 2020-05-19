package test.fr.gouv.stopc.robertserver.ws.dto.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.CollectionUtils;

import com.google.protobuf.ByteString;

import fr.gouv.stopc.robert.crypto.grpc.server.response.EphemeralTupleResponse;
import fr.gouv.stopc.robertserver.ws.dto.EpochKeyBundleDto;
import fr.gouv.stopc.robertserver.ws.dto.mapper.EpochKeyBundleDtoMapper;

@ExtendWith(SpringExtension.class)
public class EpochKeyBundleDtoMapperTest {

	private EpochKeyBundleDtoMapper mapper =  new EpochKeyBundleDtoMapper();

	@Test
	public void testConvertWhenIsNull() {
		// Given
		EphemeralTupleResponse ephemeralTupleResponse = null;

		// When 
		Optional<EpochKeyBundleDto> dto = mapper.convert(ephemeralTupleResponse);
		
		// Then
		assertFalse(dto.isPresent());
	}

	@Test
	public void testConvertWhenIsNotNull() {
		int epochId = 1200;

		// Given
		EphemeralTupleResponse ephemeralTupleResponse = EphemeralTupleResponse
				.newBuilder()
				.setEbid(ByteString.copyFrom(generate(8)))
				.setEbid(ByteString.copyFrom(generate(1)))
				.setEpochId(epochId)
				.build();
		
		String expectedEbid = Base64.getEncoder().encodeToString(
				ephemeralTupleResponse.getEbid().toByteArray());
		String expectedEcc = Base64.getEncoder().encodeToString(
				ephemeralTupleResponse.getEcc().toByteArray());
		
		// When 
		Optional<EpochKeyBundleDto> dto = mapper.convert(ephemeralTupleResponse);
		
		// Then
		assertTrue(dto.isPresent());
		assertEquals(epochId, dto.get().getEpochId());
		assertNotNull(dto.get().getKey());
		assertEquals(expectedEbid, dto.get().getKey().getEbid());
		assertEquals(expectedEcc, dto.get().getKey().getEcc());
	}

	@Test
	public void testConvertListWhenIsNull() {

		// Given
		List<EphemeralTupleResponse> response = null;

		// When
		List<EpochKeyBundleDto> epochKeyBundleDtos = mapper.convert(response);

		// Then
		assertTrue(CollectionUtils.isEmpty(epochKeyBundleDtos));
	}

	@Test
	public void testConvertListWhenIsEmpty() {

		// Given
		List<EphemeralTupleResponse> response = Collections.emptyList();

		// When
		List<EpochKeyBundleDto> epochKeyBundleDtos = mapper.convert(response);

		// Then
		assertTrue(CollectionUtils.isEmpty(epochKeyBundleDtos));
	}

	@Test
	public void testConvertListWhenIsNotEmpty() {
		int epochId = 1200;

		// Given
		EphemeralTupleResponse ephemeralTupleResponse = EphemeralTupleResponse
				.newBuilder()
				.setEbid(ByteString.copyFrom(generate(8)))
				.setEbid(ByteString.copyFrom(generate(1)))
				.setEpochId(epochId)
				.build();

		String expectedEbid = Base64.getEncoder().encodeToString(
				ephemeralTupleResponse.getEbid().toByteArray());
		String expectedEcc = Base64.getEncoder().encodeToString(
				ephemeralTupleResponse.getEcc().toByteArray());

		List<EphemeralTupleResponse> response = new ArrayList<>();
		response.add(ephemeralTupleResponse);

		// When
		List<EpochKeyBundleDto> epochKeyBundleDtos = mapper.convert(response);

		// Then
		assertFalse(CollectionUtils.isEmpty(epochKeyBundleDtos));
		assertThat(1,  equalTo(epochKeyBundleDtos.size()));
		
		EpochKeyBundleDto epochKeyBundleDto = epochKeyBundleDtos.get(0);
		
		assertEquals(epochId, epochKeyBundleDto.getEpochId());
		assertNotNull(epochKeyBundleDto.getKey());
		assertEquals(expectedEbid, epochKeyBundleDto.getKey().getEbid());
		assertEquals(expectedEcc, epochKeyBundleDto.getKey().getEcc());
	}


	private byte[] generate(final int nbOfbytes) {
		byte[] rndBytes = new byte[nbOfbytes];
		SecureRandom sr = new SecureRandom();
		sr.nextBytes(rndBytes);
		return rndBytes;
	}
}