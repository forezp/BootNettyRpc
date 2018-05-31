package io.github.forezp.netty.rpc.core.protocol.serializer;

import io.github.forezp.netty.rpc.core.common.exception.CommonException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JDKSerializer {
    public static <T> byte[] serialize(T object) {
        if (object == null) {
            throw new CommonException( "Object is null" );
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        byte[] bytes = null;
        try {
            oos = new ObjectOutputStream( baos );
            oos.writeObject( object );
            bytes = baos.toByteArray();
        } catch (Exception e) {
            throw new CommonException( e.getMessage() );
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (Exception e) {

            } finally {
                oos = null;
            }

            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {

            } finally {
                baos = null;
            }

        }

        return bytes;
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            throw new CommonException( "Bytes is null or empty" );
        }

        ByteArrayInputStream bais = new ByteArrayInputStream( bytes );
        ObjectInputStream ois = null;
        Object object = null;
        try {
            ois = new ObjectInputStream( bais );
            object = ois.readObject();
        } catch (Exception e) {
            throw new CommonException( e.getMessage() );
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (Exception e) {

            } finally {
                ois = null;
            }

            try {
                if (bais != null) {
                    bais.close();
                }
            } catch (Exception e) {

            } finally {
                bais = null;
            }
        }

        return (T) object;
    }
}