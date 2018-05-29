package io.github.forezp.netty.rpc.core.protocol.serializer;


public class SerializerExecutor {

    public static <T> byte[] serialize(T object) {
        byte[] bytes = null;

        bytes = JDKSerializer.serialize( object );
        return bytes;
    }

    public static <T> T deserialize(byte[] bytes) {

        T object = null;

        object = JDKSerializer.deserialize( bytes );

        return object;
    }
}