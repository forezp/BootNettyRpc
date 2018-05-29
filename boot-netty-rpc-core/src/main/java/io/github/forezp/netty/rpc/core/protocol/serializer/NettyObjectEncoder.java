package io.github.forezp.netty.rpc.core.protocol.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.ReferenceCountUtil;
import java.io.Serializable;

public class NettyObjectEncoder extends MessageToByteEncoder<Object> {
    public NettyObjectEncoder() {

    }

    @Override
    protected void encode(ChannelHandlerContext context, Object object, ByteBuf buf) throws Exception {
        byte[] bytes = null;
        try {
            if (buf == null) {
                return;
            }

            if (!(object instanceof Serializable)) {
                return;
            }
            bytes = SerializerExecutor.serialize((Serializable) object);

            buf.writeInt(bytes.length);
            buf.writeBytes(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            ReferenceCountUtil.release(bytes);
        }
    }
}