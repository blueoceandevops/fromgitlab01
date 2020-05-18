package test.fr.gouv.stopc.robert.crypto.grpc.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.CollectionUtils;

import com.google.protobuf.ByteString;

import fr.gouv.stopc.robert.crypto.grpc.server.CryptoServiceGrpcServer;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.CryptoGrpcServiceImplGrpc;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.CryptoGrpcServiceImplGrpc.CryptoGrpcServiceImplImplBase;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.CryptoGrpcServiceImplGrpc.CryptoGrpcServiceImplStub;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.DecryptCountryCodeRequest;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.DecryptCountryCodeResponse;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.DecryptEBIDRequest;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.EBIDResponse;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.EncryptCountryCodeRequest;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.EncryptCountryCodeResponse;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.EncryptedEphemeralTupleRequest;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.EncryptedEphemeralTupleResponse;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.EphemeralTupleRequest;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.EphemeralTupleResponse;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.GenerateEBIDRequest;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.GenerateIdentityRequest;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.GenerateIdentityResponse;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.MacEsrValidationRequest;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.MacHelloGenerationRequest;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.MacHelloGenerationResponse;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.MacHelloValidationRequest;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.MacValidationForTypeRequest;
import fr.gouv.stopc.robert.crypto.grpc.server.messaging.MacValidationResponse;
import fr.gouv.stopc.robert.crypto.grpc.server.service.IClientKeyStorageService;
import fr.gouv.stopc.robert.crypto.grpc.server.service.ICryptoServerConfigurationService;
import fr.gouv.stopc.robert.crypto.grpc.server.service.IECDHKeyService;
import fr.gouv.stopc.robert.crypto.grpc.server.service.impl.ClientKeyStorageService;
import fr.gouv.stopc.robert.crypto.grpc.server.service.impl.CryptoGrpcServiceBaseImpl;
import fr.gouv.stopc.robert.crypto.grpc.server.service.impl.CryptoServerConfigurationServiceImpl;
import fr.gouv.stopc.robert.crypto.grpc.server.service.impl.ECDHKeyServiceImpl;
import fr.gouv.stopc.robert.server.common.DigestSaltEnum;
import fr.gouv.stopc.robert.server.common.utils.ByteUtils;
import fr.gouv.stopc.robert.server.crypto.service.CryptoService;
import fr.gouv.stopc.robert.server.crypto.service.impl.CryptoServiceImpl;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.testing.GrpcCleanupRule;
import test.fr.gouv.stopc.robert.crypto.grpc.server.utils.CryptoTestUtils;


@ExtendWith(SpringExtension.class)
public class CryptoServiceGrpcServerTest {

    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();
    private ManagedChannel inProcessChannel;

    private CryptoServiceGrpcServer server;

    private CryptoGrpcServiceImplImplBase service;

    private ICryptoServerConfigurationService serverConfigurationService;

    private CryptoService cryptoService;

    private IECDHKeyService keyService;

    private IClientKeyStorageService clientStorageService;

    @BeforeEach
    public void beforeEach() throws IOException {

        serverConfigurationService = new CryptoServerConfigurationServiceImpl();

        cryptoService=  new CryptoServiceImpl();

        keyService = new ECDHKeyServiceImpl();

        clientStorageService = new ClientKeyStorageService();

        service = new CryptoGrpcServiceBaseImpl(serverConfigurationService,
                cryptoService,
                keyService,
                clientStorageService);

        String serverName = InProcessServerBuilder.generateName();

        server = new CryptoServiceGrpcServer(
                InProcessServerBuilder.forName(serverName)
                .directExecutor()
                , 0, service);
        server.start();
        inProcessChannel = grpcCleanup.register(
                InProcessChannelBuilder.forName(serverName).directExecutor().build());

    }

    @AfterEach
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testGenerateEphemeralTuple()  {

        try {
            // Given
            EphemeralTupleRequest request = EphemeralTupleRequest.newBuilder()
                    .setIdA(ByteString.copyFrom(ByteUtils.generateRandom(5)))
                    .setCountryCode(ByteString.copyFrom(ByteUtils.generateRandom(1)))
                    .setFromEpoch(2100)
                    .setNumberOfEpochsToGenerate(1)
                    .build();
            CryptoGrpcServiceImplStub stub = CryptoGrpcServiceImplGrpc.newStub(inProcessChannel);

            final List<EphemeralTupleResponse> response = new ArrayList<>();
            final CountDownLatch latch = new CountDownLatch(1);

            StreamObserver<EphemeralTupleResponse> responseObserver =
                    new StreamObserver<EphemeralTupleResponse>() {
                @Override
                public void onNext(EphemeralTupleResponse value) {
                    response.add(value);
                }

                @Override
                public void onError(Throwable t) {
                    fail();
                }

                @Override
                public void onCompleted() {
                    latch.countDown();
                }
            };

            // When
            stub.generateEphemeralTuple(request, responseObserver);

            // Then
            assertTrue(latch.await(1, TimeUnit.SECONDS));
            assertEquals(1, response.size());
        } catch (InterruptedException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGenerateEBID() {
        try {
            // Given
            GenerateEBIDRequest request = GenerateEBIDRequest
                    .newBuilder()
                    .setIdA(ByteString.copyFrom(ByteUtils.generateRandom(5))).build();

            CryptoGrpcServiceImplStub stub = CryptoGrpcServiceImplGrpc.newStub(inProcessChannel);

            final List<EBIDResponse> response = new ArrayList<EBIDResponse>();

            final CountDownLatch latch = new CountDownLatch(1);

            StreamObserver<EBIDResponse> responseObserver =
                    new StreamObserver<EBIDResponse>() {
                @Override
                public void onNext(EBIDResponse value) {
                    response.add(value);
                }

                @Override
                public void onError(Throwable t) {
                    fail();
                }

                @Override
                public void onCompleted() {
                    latch.countDown();
                }
            };

            // When 
            stub.generateEBID(request, responseObserver);

            // Then
            assertTrue(latch.await(1, TimeUnit.SECONDS));
            assertEquals(1, response.size());
            assertTrue(ByteUtils.isNotEmpty(response.get(0).getEbid().toByteArray()));
        } catch (InterruptedException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDecryptEBID() {

        try {
            // Given
            DecryptEBIDRequest request = DecryptEBIDRequest.newBuilder()
                    .setEbid(ByteString.copyFrom(ByteUtils.generateRandom(8))).build();

            CryptoGrpcServiceImplStub stub = CryptoGrpcServiceImplGrpc.newStub(inProcessChannel);

            List<EBIDResponse> response = new ArrayList<EBIDResponse>();

            final CountDownLatch latch = new CountDownLatch(1);

            StreamObserver<EBIDResponse> responseObserver =
                    new StreamObserver<EBIDResponse>() {
                @Override
                public void onNext(EBIDResponse value) {
                    response.add(value);
                }

                @Override
                public void onError(Throwable t) {
                    fail();
                }

                @Override
                public void onCompleted() {
                    latch.countDown();
                }
            };

            // When
            stub.decryptEBID(request, responseObserver);

            // Then
            assertTrue(latch.await(1, TimeUnit.SECONDS));
            assertEquals(1, response.size());
            assertTrue(ByteUtils.isNotEmpty(response.get(0).getEbid().toByteArray()));
        } catch (InterruptedException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void testEncryptCountryCode() {

        try {
            // Given
            EncryptCountryCodeRequest request = EncryptCountryCodeRequest.newBuilder()
                    .setEbid(ByteString.copyFrom(ByteUtils.generateRandom(8)))
                    .setCountryCode(ByteString.copyFrom(ByteUtils.generateRandom(1))).build();

            CryptoGrpcServiceImplStub stub = CryptoGrpcServiceImplGrpc.newStub(inProcessChannel);

            List<EncryptCountryCodeResponse> response = new ArrayList<EncryptCountryCodeResponse>();

            final CountDownLatch latch = new CountDownLatch(1);

            StreamObserver<EncryptCountryCodeResponse> responseObserver =
                    new StreamObserver<EncryptCountryCodeResponse>() {
                @Override
                public void onNext(EncryptCountryCodeResponse value) {
                    response.add(value);
                }

                @Override
                public void onError(Throwable t) {
                    fail();
                }

                @Override
                public void onCompleted() {
                    latch.countDown();
                }
            };

            // When
            stub.encryptCountryCode(request, responseObserver);

            // Then
            assertTrue(latch.await(1, TimeUnit.SECONDS));
            assertEquals(1, response.size());
            assertTrue(ByteUtils.isNotEmpty(response.get(0).getEncryptedCountryCode().toByteArray()));
        } catch (InterruptedException e) {
            fail(e.getMessage());
        }


    }

    @Test
    public void testDecryptCountryCode() {

        // Then
        try {
            // Given
            DecryptCountryCodeRequest request = DecryptCountryCodeRequest.newBuilder()
                    .setEbid(ByteString.copyFrom(ByteUtils.generateRandom(8)))
                    .setEncryptedCountryCode(ByteString.copyFrom(ByteUtils.generateRandom(1)))
                    .build();

            CryptoGrpcServiceImplStub stub = CryptoGrpcServiceImplGrpc.newStub(inProcessChannel);

            List<DecryptCountryCodeResponse> response = new ArrayList<DecryptCountryCodeResponse>();

            final CountDownLatch latch = new CountDownLatch(1);


            StreamObserver<DecryptCountryCodeResponse> responseObserver =
                    new StreamObserver<DecryptCountryCodeResponse>() {
                @Override
                public void onNext(DecryptCountryCodeResponse value) {
                    response.add(value);
                }

                @Override
                public void onError(Throwable t) {
                    fail();
                }

                @Override
                public void onCompleted() {
                    latch.countDown();
                }
            };

            // When
            stub.decryptCountryCode(request, responseObserver);

            // Then
            assertEquals(1, response.size());
            assertTrue(ByteUtils.isNotEmpty(response.get(0).getCountryCode().toByteArray()));
            assertTrue(latch.await(1, TimeUnit.SECONDS));

        } catch (InterruptedException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGenerateMacHello() {

        try {
            // Given
            MacHelloGenerationRequest request  = MacHelloGenerationRequest.newBuilder()
                    .setKa(ByteString.copyFrom(ByteUtils.generateRandom(16)))
                    .setHelloMessage(ByteString.copyFrom(ByteUtils.generateRandom(16)))
                    .build();

            CryptoGrpcServiceImplStub stub = CryptoGrpcServiceImplGrpc.newStub(inProcessChannel);

            List<MacHelloGenerationResponse> response = new ArrayList<>();

            final CountDownLatch latch = new CountDownLatch(1);


            StreamObserver<MacHelloGenerationResponse> responseObserver =
                    new StreamObserver<MacHelloGenerationResponse>() {
                @Override
                public void onNext(MacHelloGenerationResponse value) {
                    response.add(value);
                }

                @Override
                public void onError(Throwable t) {
                    fail();
                }

                @Override
                public void onCompleted() {
                    latch.countDown();
                }
            };

            // When
            stub.generateMacHello(request, responseObserver);

            // Then
            assertTrue(latch.await(1, TimeUnit.SECONDS));
            assertEquals(1, response.size());
            assertTrue(ByteUtils.isNotEmpty(response.get(0).getMacHelloMessage().toByteArray()));

        } catch (InterruptedException e) {
            fail(e.getMessage());
        }
    }


    @Test
    public void testValidateMacHello() {

        try {
            // Given
            MacHelloValidationRequest request = MacHelloValidationRequest.newBuilder()
                    .setKa(ByteString.copyFrom(ByteUtils.generateRandom(16)))
                    .setDataToValidate(ByteString.copyFrom(ByteUtils.generateRandom(16)))
                    .build();

            CryptoGrpcServiceImplStub stub = CryptoGrpcServiceImplGrpc.newStub(inProcessChannel);

            List<MacValidationResponse> response = new ArrayList<>();

            final CountDownLatch latch = new CountDownLatch(1);


            StreamObserver<MacValidationResponse> responseObserver =
                    new StreamObserver<MacValidationResponse>() {
                @Override
                public void onNext(MacValidationResponse value) {
                    response.add(value);
                }

                @Override
                public void onError(Throwable t) {
                    fail();
                }

                @Override
                public void onCompleted() {
                    latch.countDown();
                }
            };

            // When
            stub.validateMacHello(request, responseObserver);

            //
            assertTrue(latch.await(1, TimeUnit.SECONDS));
            assertEquals(1, response.size());
            assertFalse(response.get(0).getIsValid());
        } catch (InterruptedException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testValidateMacEsr() {

        try {
            // Given
            MacEsrValidationRequest request = MacEsrValidationRequest.newBuilder()
                    .setKa(ByteString.copyFrom(ByteUtils.generateRandom(16)))
                    .setDataToValidate(ByteString.copyFrom(ByteUtils.generateRandom(12)))
                    .setMacToMatchWith(ByteString.copyFrom(ByteUtils.generateRandom(16)))
                    .build();

            CryptoGrpcServiceImplStub stub = CryptoGrpcServiceImplGrpc.newStub(inProcessChannel);

            List<MacValidationResponse> response = new ArrayList<>();

            final CountDownLatch latch = new CountDownLatch(1);


            StreamObserver<MacValidationResponse> responseObserver =
                    new StreamObserver<MacValidationResponse>() {
                @Override
                public void onNext(MacValidationResponse value) {
                    response.add(value);
                }

                @Override
                public void onError(Throwable t) {
                    fail();
                }

                @Override
                public void onCompleted() {
                    latch.countDown();
                }
            };

            // When
            stub.validateMacEsr(request, responseObserver);

            //
            assertTrue(latch.await(1, TimeUnit.SECONDS));
            assertEquals(1, response.size());
            assertFalse(response.get(0).getIsValid());
        } catch (InterruptedException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testValidateMacForType() {

        try {
            // Given
            MacValidationForTypeRequest request = MacValidationForTypeRequest.newBuilder()
                    .setKa(ByteString.copyFrom(ByteUtils.generateRandom(16)))
                    .setDataToValidate(ByteString.copyFrom(ByteUtils.generateRandom(12)))
                    .setMacToMatchWith(ByteString.copyFrom(ByteUtils.generateRandom(16)))
                    .setPrefixe(ByteString.copyFrom(new byte[] { DigestSaltEnum.UNREGISTER.getValue() }))
                    .build();

            CryptoGrpcServiceImplStub stub = CryptoGrpcServiceImplGrpc.newStub(inProcessChannel);

            List<MacValidationResponse> response = new ArrayList<>();

            final CountDownLatch latch = new CountDownLatch(1);


            StreamObserver<MacValidationResponse> responseObserver =
                    new StreamObserver<MacValidationResponse>() {
                @Override
                public void onNext(MacValidationResponse value) {
                    response.add(value);
                }

                @Override
                public void onError(Throwable t) {
                    fail();
                }

                @Override
                public void onCompleted() {
                    latch.countDown();
                }
            };

            // When
            stub.validateMacForType(request, responseObserver);

            //
            assertTrue(latch.await(1, TimeUnit.SECONDS));
            assertEquals(1, response.size());
            assertFalse(response.get(0).getIsValid());
        } catch (InterruptedException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testRejectInvalidDigestSalt() {
        List<MacValidationResponse> response = new ArrayList<>();

        // Given
        MacValidationForTypeRequest request = MacValidationForTypeRequest.newBuilder()
                .setKa(ByteString.copyFrom(ByteUtils.generateRandom(16)))
                .setDataToValidate(ByteString.copyFrom(ByteUtils.generateRandom(12)))
                .setMacToMatchWith(ByteString.copyFrom(ByteUtils.generateRandom(16)))
                .setPrefixe(ByteString.copyFrom(new byte[] { (byte)0xFF }))
                .build();

        CryptoGrpcServiceImplStub stub = CryptoGrpcServiceImplGrpc.newStub(inProcessChannel);

        List<Throwable> exceptions  = new ArrayList<>();

        final CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<MacValidationResponse> responseObserver =
                new StreamObserver<MacValidationResponse>() {
            @Override
            public void onNext(MacValidationResponse value) {
                response.add(value);
            }

            @Override
            public void onError(Throwable t) {

                exceptions.add(t);
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };

        stub.validateMacForType(request, responseObserver);

        // When
        assertFalse(CollectionUtils.isEmpty(exceptions));
        assertEquals(1, exceptions.size());
        assertTrue(exceptions.get(0) instanceof StatusRuntimeException);
    }

    @Test
    public void testGenerateIdentityWhenBadClientPublicKey() {

        // Given
        GenerateIdentityRequest request = GenerateIdentityRequest.newBuilder()
                .setClientPublicKey(ByteString.copyFrom(ByteUtils.generateRandom(32)))
                .build();

        CryptoGrpcServiceImplStub stub = CryptoGrpcServiceImplGrpc.newStub(inProcessChannel);

        List<Throwable> exceptions  = new ArrayList<>();

        final CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<GenerateIdentityResponse> responseObserver =
                new StreamObserver<GenerateIdentityResponse>() {
            @Override
            public void onNext(GenerateIdentityResponse value) {
                fail();
            }

            @Override
            public void onError(Throwable t) {
                exceptions.add(t);
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };


        // When
        stub.generateIdentity(request, responseObserver);

        // Then
        assertFalse(CollectionUtils.isEmpty(exceptions));
        assertEquals(1, exceptions.size());
        assertTrue(exceptions.get(0) instanceof StatusRuntimeException);
    }

    @Test
    public void testGenerateIdentityWhenGoodClientPublicKey() {

        try {
            // Given
            byte[] clientPublicKey = CryptoTestUtils.generateECDHPublicKey();
            GenerateIdentityRequest request = GenerateIdentityRequest.newBuilder()
                    .setClientPublicKey(ByteString.copyFrom(clientPublicKey))
                    .build();

            CryptoGrpcServiceImplStub stub = CryptoGrpcServiceImplGrpc.newStub(inProcessChannel);

            List<GenerateIdentityResponse> response = new ArrayList<>();

            final CountDownLatch latch = new CountDownLatch(1);

            StreamObserver<GenerateIdentityResponse> responseObserver =
                    new StreamObserver<GenerateIdentityResponse>() {

                @Override
                public void onNext(GenerateIdentityResponse value) {
                    response.add(value);
                }

                @Override
                public void onError(Throwable t) {
                    fail();
                }

                @Override
                public void onCompleted() {
                    latch.countDown();
                }
            };

            // When
            stub.generateIdentity(request, responseObserver);

            //
            assertTrue(latch.await(1, TimeUnit.SECONDS));
            assertEquals(1, response.size());
            assertNotNull(response.get(0).getIdA());
        } catch (InterruptedException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGenerateEncryptedEphemeralTupleWhenBadClientPublicKey() {

        // Given
        EncryptedEphemeralTupleRequest request = EncryptedEphemeralTupleRequest.newBuilder()
                .setClientPublicKey(ByteString.copyFrom(ByteUtils.generateRandom(32)))
                .setIdA(ByteString.copyFrom(ByteUtils.generateRandom(5)))
                .setCountryCode(ByteString.copyFrom(ByteUtils.generateRandom(1)))
                .setFromEpoch(2100)
                .setNumberOfEpochsToGenerate(1)
                .build();

        CryptoGrpcServiceImplStub stub = CryptoGrpcServiceImplGrpc.newStub(inProcessChannel);

        List<Throwable> exceptions  = new ArrayList<>();

        final CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<EncryptedEphemeralTupleResponse> responseObserver =
                new StreamObserver<EncryptedEphemeralTupleResponse>() {
            @Override
            public void onNext(EncryptedEphemeralTupleResponse value) {
                fail();
            }

            @Override
            public void onError(Throwable t) {
                exceptions.add(t);
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };


        // When
        stub.generateEncryptedEphemeralTuple(request, responseObserver);

        // Then
        assertFalse(CollectionUtils.isEmpty(exceptions));
        assertEquals(1, exceptions.size());
        assertTrue(exceptions.get(0) instanceof StatusRuntimeException);
    }

    @Test
    public void testGenerateEncryptedEphemeralTupleWhenGoodClientPublicKey() {

        try {
            // Given
            byte[] clientPublicKey = CryptoTestUtils.generateECDHPublicKey();
            EncryptedEphemeralTupleRequest request = EncryptedEphemeralTupleRequest.newBuilder()
                    .setClientPublicKey(ByteString.copyFrom(clientPublicKey))
                    .setIdA(ByteString.copyFrom(ByteUtils.generateRandom(5)))
                    .setCountryCode(ByteString.copyFrom(ByteUtils.generateRandom(1)))
                    .setFromEpoch(2100)
                    .setNumberOfEpochsToGenerate(1)
                    .build();

            CryptoGrpcServiceImplStub stub = CryptoGrpcServiceImplGrpc.newStub(inProcessChannel);

            List<EncryptedEphemeralTupleResponse> response = new ArrayList<>();

            final CountDownLatch latch = new CountDownLatch(1);

            StreamObserver<EncryptedEphemeralTupleResponse> responseObserver =
                    new StreamObserver<EncryptedEphemeralTupleResponse>() {

                @Override
                public void onNext(EncryptedEphemeralTupleResponse value) {
                    response.add(value);
                }

                @Override
                public void onError(Throwable t) {
                    fail();
                }

                @Override
                public void onCompleted() {
                    latch.countDown();
                }
            };

            // When
            stub.generateEncryptedEphemeralTuple(request, responseObserver);

            //
            assertTrue(latch.await(1, TimeUnit.SECONDS));
            assertEquals(1, response.size());
            assertNotNull(response.get(0).getEncryptedTuples());
            assertNotNull(response.get(0).getServerPublicKeyForTuples());
        } catch (InterruptedException e) {
            fail(e.getMessage());
        }
    }
}
