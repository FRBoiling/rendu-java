// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: R2GM.proto

package protocol.relation.global;

public final class R2GM {
  private R2GM() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MSG_R2GM_HEARTBEATOrBuilder extends
      // @@protoc_insertion_point(interface_extends:protocol.relation.global.MSG_R2GM_HEARTBEAT)
      com.google.protobuf.MessageOrBuilder {
  }
  /**
   * Protobuf type {@code protocol.relation.global.MSG_R2GM_HEARTBEAT}
   */
  public  static final class MSG_R2GM_HEARTBEAT extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:protocol.relation.global.MSG_R2GM_HEARTBEAT)
      MSG_R2GM_HEARTBEATOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use MSG_R2GM_HEARTBEAT.newBuilder() to construct.
    private MSG_R2GM_HEARTBEAT(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private MSG_R2GM_HEARTBEAT() {
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private MSG_R2GM_HEARTBEAT(
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
      return protocol.relation.global.R2GM.internal_static_protocol_relation_global_MSG_R2GM_HEARTBEAT_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return protocol.relation.global.R2GM.internal_static_protocol_relation_global_MSG_R2GM_HEARTBEAT_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT.class, protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT.Builder.class);
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT)) {
        return super.equals(obj);
      }
      protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT other = (protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT) obj;

      boolean result = true;
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
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
     * Protobuf type {@code protocol.relation.global.MSG_R2GM_HEARTBEAT}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:protocol.relation.global.MSG_R2GM_HEARTBEAT)
        protocol.relation.global.R2GM.MSG_R2GM_HEARTBEATOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return protocol.relation.global.R2GM.internal_static_protocol_relation_global_MSG_R2GM_HEARTBEAT_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return protocol.relation.global.R2GM.internal_static_protocol_relation_global_MSG_R2GM_HEARTBEAT_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT.class, protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT.Builder.class);
      }

      // Construct using protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT.newBuilder()
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
      public Builder clear() {
        super.clear();
        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return protocol.relation.global.R2GM.internal_static_protocol_relation_global_MSG_R2GM_HEARTBEAT_descriptor;
      }

      public protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT getDefaultInstanceForType() {
        return protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT.getDefaultInstance();
      }

      public protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT build() {
        protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT buildPartial() {
        protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT result = new protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT(this);
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT) {
          return mergeFrom((protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT other) {
        if (other == protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT.getDefaultInstance()) return this;
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:protocol.relation.global.MSG_R2GM_HEARTBEAT)
    }

    // @@protoc_insertion_point(class_scope:protocol.relation.global.MSG_R2GM_HEARTBEAT)
    private static final protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT();
    }

    public static protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @java.lang.Deprecated public static final com.google.protobuf.Parser<MSG_R2GM_HEARTBEAT>
        PARSER = new com.google.protobuf.AbstractParser<MSG_R2GM_HEARTBEAT>() {
      public MSG_R2GM_HEARTBEAT parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new MSG_R2GM_HEARTBEAT(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<MSG_R2GM_HEARTBEAT> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<MSG_R2GM_HEARTBEAT> getParserForType() {
      return PARSER;
    }

    public protocol.relation.global.R2GM.MSG_R2GM_HEARTBEAT getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_protocol_relation_global_MSG_R2GM_HEARTBEAT_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_protocol_relation_global_MSG_R2GM_HEARTBEAT_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\nR2GM.proto\022\030protocol.relation.global\"\024" +
      "\n\022MSG_R2GM_HEARTBEAT"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_protocol_relation_global_MSG_R2GM_HEARTBEAT_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_protocol_relation_global_MSG_R2GM_HEARTBEAT_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_protocol_relation_global_MSG_R2GM_HEARTBEAT_descriptor,
        new java.lang.String[] { });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
