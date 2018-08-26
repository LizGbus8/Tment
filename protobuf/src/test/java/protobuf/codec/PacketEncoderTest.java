package protobuf.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import protobuf.ParseRegistryMap;
import protobuf.generate.internal.Internal;

import java.io.IOException;


/**
 * 作者：LizG on 2018/8/26 20:44
 * 描述：
 */
@Slf4j
public class PacketEncoderTest {

    @Test
    public void encode() throws IOException {
        ParseRegistryMap.initRegistry();

        /** 包装数据 */
        int ptoNum = 1000;
        Internal.GTransfer.Builder builder = Internal.GTransfer.newBuilder();
        builder.setDest(Internal.Dest.Gate)
                .setNetId(12)
                .setPtoNum(1000)
                .setUserId("837")
                .setMsg(com.google.protobuf.ByteString.copyFrom("".getBytes()));

        Internal.GTransfer gTransfer = builder.build();

        int length = gTransfer.toByteArray().length;

        EmbeddedChannel channel = new EmbeddedChannel(new PacketEncoder());
        channel.writeOutbound(gTransfer);
        ByteBuf o = (ByteBuf)channel.readOutbound();

        log.info("Test after magic_num:{},ptoNum:{},length:{},message_length:{}",o.readInt(),o.readInt(),o.readInt(),length);
    }
}
