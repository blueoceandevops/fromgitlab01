package fr.gouv.stopc.robert.crypto.grpc.server.service.impl;

import fr.gouv.stopc.robert.server.common.utils.TimeUtils;
import org.springframework.stereotype.Service;

import fr.gouv.stopc.robert.crypto.grpc.server.service.ICryptoServerConfigurationService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


/**
 * TODO : remove or update this class
 * Implementation of a service while waiting for a server repository to be accessible.
 */
@Service
public class CryptoServerConfigurationServiceImpl implements ICryptoServerConfigurationService {

    @Override
    public long getServiceTimeStart() {
        final LocalDateTime ldt = LocalDateTime.of(2020, 6, 1, 00, 00);
        final ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        return TimeUtils.convertUnixMillistoNtpSeconds(zdt.toInstant().toEpochMilli());
    }

    @Override
    public int getHelloMessageTimestampTolerance() {
        return 180;
    }
}
