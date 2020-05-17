// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: crypto_service.proto

package fr.gouv.stopc.robert.crypto.grpc.server.response;

import fr.gouv.stopc.robert.crypto.grpc.server.service.CryptoGrpcService;

/**
 * Protobuf type {@code robert.server.crypto.GenerateIdentityResponse}
 */
public  final class GenerateIdentityResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:robert.server.crypto.GenerateIdentityResponse)
    GenerateIdentityResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use GenerateIdentityResponse.newBuilder() to construct.
  private GenerateIdentityResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private GenerateIdentityResponse() {
    idA_ = com.google.protobuf.ByteString.EMPTY;
    encryptedSharedKey_ = com.google.protobuf.ByteString.EMPTY;
    serverPublicKeyForKey_ = com.google.protobuf.ByteString.EMPTY;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new GenerateIdentityResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private GenerateIdentityResponse(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {

            idA_ = input.readBytes();
            break;
          }
          case 18: {

            encryptedSharedKey_ = input.readBytes();
            break;
          }
          case 26: {

            serverPublicKeyForKey_ = input.readBytes();
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return CryptoGrpcService.internal_static_robert_server_crypto_GenerateIdentityResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return CryptoGrpcService.internal_static_robert_server_crypto_GenerateIdentityResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            GenerateIdentityResponse.class, GenerateIdentityResponse.Builder.class);
  }

  public static final int IDA_FIELD_NUMBER = 1;
  private com.google.protobuf.ByteString idA_;
  /**
   * <code>bytes idA = 1;</code>
   * @return The idA.
   */
  public com.google.protobuf.ByteString getIdA() {
    return idA_;
  }

  public static final int ENCRYPTEDSHAREDKEY_FIELD_NUMBER = 2;
  private com.google.protobuf.ByteString encryptedSharedKey_;
  /**
   * <code>bytes encryptedSharedKey = 2;</code>
   * @return The encryptedSharedKey.
   */
  public com.google.protobuf.ByteString getEncryptedSharedKey() {
    return encryptedSharedKey_;
  }

  public static final int SERVERPUBLICKEYFORKEY_FIELD_NUMBER = 3;
  private com.google.protobuf.ByteString serverPublicKeyForKey_;
  /**
   * <code>bytes serverPublicKeyForKey = 3;</code>
   * @return The serverPublicKeyForKey.
   */
  public com.google.protobuf.ByteString getServerPublicKeyForKey() {
    return serverPublicKeyForKey_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!idA_.isEmpty()) {
      output.writeBytes(1, idA_);
    }
    if (!encryptedSharedKey_.isEmpty()) {
      output.writeBytes(2, encryptedSharedKey_);
    }
    if (!serverPublicKeyForKey_.isEmpty()) {
      output.writeBytes(3, serverPublicKeyForKey_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!idA_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(1, idA_);
    }
    if (!encryptedSharedKey_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(2, encryptedSharedKey_);
    }
    if (!serverPublicKeyForKey_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(3, serverPublicKeyForKey_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof GenerateIdentityResponse)) {
      return super.equals(obj);
    }
    GenerateIdentityResponse other = (GenerateIdentityResponse) obj;

    if (!getIdA()
        .equals(other.getIdA())) return false;
    if (!getEncryptedSharedKey()
        .equals(other.getEncryptedSharedKey())) return false;
    if (!getServerPublicKeyForKey()
        .equals(other.getServerPublicKeyForKey())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + IDA_FIELD_NUMBER;
    hash = (53 * hash) + getIdA().hashCode();
    hash = (37 * hash) + ENCRYPTEDSHAREDKEY_FIELD_NUMBER;
    hash = (53 * hash) + getEncryptedSharedKey().hashCode();
    hash = (37 * hash) + SERVERPUBLICKEYFORKEY_FIELD_NUMBER;
    hash = (53 * hash) + getServerPublicKeyForKey().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static GenerateIdentityResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static GenerateIdentityResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static GenerateIdentityResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static GenerateIdentityResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static GenerateIdentityResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static GenerateIdentityResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static GenerateIdentityResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static GenerateIdentityResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static GenerateIdentityResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static GenerateIdentityResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static GenerateIdentityResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static GenerateIdentityResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(GenerateIdentityResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code robert.server.crypto.GenerateIdentityResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:robert.server.crypto.GenerateIdentityResponse)
      GenerateIdentityResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return CryptoGrpcService.internal_static_robert_server_crypto_GenerateIdentityResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return CryptoGrpcService.internal_static_robert_server_crypto_GenerateIdentityResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              GenerateIdentityResponse.class, GenerateIdentityResponse.Builder.class);
    }

    // Construct using GenerateIdentityResponse.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      idA_ = com.google.protobuf.ByteString.EMPTY;

      encryptedSharedKey_ = com.google.protobuf.ByteString.EMPTY;

      serverPublicKeyForKey_ = com.google.protobuf.ByteString.EMPTY;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return CryptoGrpcService.internal_static_robert_server_crypto_GenerateIdentityResponse_descriptor;
    }

    @java.lang.Override
    public GenerateIdentityResponse getDefaultInstanceForType() {
      return GenerateIdentityResponse.getDefaultInstance();
    }

    @java.lang.Override
    public GenerateIdentityResponse build() {
      GenerateIdentityResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public GenerateIdentityResponse buildPartial() {
      GenerateIdentityResponse result = new GenerateIdentityResponse(this);
      result.idA_ = idA_;
      result.encryptedSharedKey_ = encryptedSharedKey_;
      result.serverPublicKeyForKey_ = serverPublicKeyForKey_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof GenerateIdentityResponse) {
        return mergeFrom((GenerateIdentityResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(GenerateIdentityResponse other) {
      if (other == GenerateIdentityResponse.getDefaultInstance()) return this;
      if (other.getIdA() != com.google.protobuf.ByteString.EMPTY) {
        setIdA(other.getIdA());
      }
      if (other.getEncryptedSharedKey() != com.google.protobuf.ByteString.EMPTY) {
        setEncryptedSharedKey(other.getEncryptedSharedKey());
      }
      if (other.getServerPublicKeyForKey() != com.google.protobuf.ByteString.EMPTY) {
        setServerPublicKeyForKey(other.getServerPublicKeyForKey());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      GenerateIdentityResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (GenerateIdentityResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.google.protobuf.ByteString idA_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes idA = 1;</code>
     * @return The idA.
     */
    public com.google.protobuf.ByteString getIdA() {
      return idA_;
    }
    /**
     * <code>bytes idA = 1;</code>
     * @param value The idA to set.
     * @return This builder for chaining.
     */
    public Builder setIdA(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      idA_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bytes idA = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearIdA() {
      
      idA_ = getDefaultInstance().getIdA();
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString encryptedSharedKey_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes encryptedSharedKey = 2;</code>
     * @return The encryptedSharedKey.
     */
    public com.google.protobuf.ByteString getEncryptedSharedKey() {
      return encryptedSharedKey_;
    }
    /**
     * <code>bytes encryptedSharedKey = 2;</code>
     * @param value The encryptedSharedKey to set.
     * @return This builder for chaining.
     */
    public Builder setEncryptedSharedKey(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      encryptedSharedKey_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bytes encryptedSharedKey = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearEncryptedSharedKey() {
      
      encryptedSharedKey_ = getDefaultInstance().getEncryptedSharedKey();
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString serverPublicKeyForKey_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes serverPublicKeyForKey = 3;</code>
     * @return The serverPublicKeyForKey.
     */
    public com.google.protobuf.ByteString getServerPublicKeyForKey() {
      return serverPublicKeyForKey_;
    }
    /**
     * <code>bytes serverPublicKeyForKey = 3;</code>
     * @param value The serverPublicKeyForKey to set.
     * @return This builder for chaining.
     */
    public Builder setServerPublicKeyForKey(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      serverPublicKeyForKey_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bytes serverPublicKeyForKey = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearServerPublicKeyForKey() {
      
      serverPublicKeyForKey_ = getDefaultInstance().getServerPublicKeyForKey();
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:robert.server.crypto.GenerateIdentityResponse)
  }

  // @@protoc_insertion_point(class_scope:robert.server.crypto.GenerateIdentityResponse)
  private static final GenerateIdentityResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new GenerateIdentityResponse();
  }

  public static GenerateIdentityResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<GenerateIdentityResponse>
      PARSER = new com.google.protobuf.AbstractParser<GenerateIdentityResponse>() {
    @java.lang.Override
    public GenerateIdentityResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new GenerateIdentityResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<GenerateIdentityResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<GenerateIdentityResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public GenerateIdentityResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
