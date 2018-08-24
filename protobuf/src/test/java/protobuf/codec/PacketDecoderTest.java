package protobuf.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;
import protobuf.ParseRegistryMap;
import protobuf.generate.internal.Internal;

import java.io.IOException;


/**
 * 作者：LizG on 2018/8/23 22:01
 * 描述：
 */
public class PacketDecoderTest {

    @Test
    public void decode() throws IOException {

        ParseRegistryMap.initRegistry();

        /** 包装数据 */
        int ptoNum = 1000;
        Internal.GTransfer.Builder builder = Internal.GTransfer.newBuilder();
        builder.setDest(Internal.Dest.Gate)
                .setNetId(12)
                .setPtoNum(1000)
                .setUserId("837")
                .setMsg(com.google.protobuf.ByteString.copyFrom("hello".getBytes()));


        Internal.GTransfer gTransfer = builder.build();

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(Constants.MAGIC_NUMBER);
        buf.writeInt(ptoNum);
        buf.writeInt(gTransfer.toByteArray().length);
        buf.writeBytes(gTransfer.toByteArray());

        ByteBuf input = buf.duplicate();//复制当前对象，复制后的对象与前对象共享缓冲区，且维护自己的独立索引

        EmbeddedChannel channel = new EmbeddedChannel(new PacketDecoder());

        channel.writeInbound(input);

        Object o = channel.readOutbound();
        System.out.println(o);
        //验证写数据返回True
        channel.finish();
        //每次读3个数据
//        Assert.assertEquals(buf.readBytes(3), channel.readInbound());
//        Assert.assertEquals(buf.readBytes(3), channel.readInbound());
//        Assert.assertEquals(buf.readBytes(3), channel.readInbound());
//        Assert.assertNull(channel.readInbound());
    }
}
