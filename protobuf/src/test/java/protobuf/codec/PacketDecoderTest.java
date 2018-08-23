package protobuf.codec;

import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;
import protobuf.analysis.ParseMap;

import java.io.IOException;


/**
 * 作者：LizG on 2018/8/23 22:01
 * 描述：
 */
public class PacketDecoderTest {

    @Test
    public void decode() throws IOException {

        /** 包装数据 */
        int ptoNum = 1004;
        String msg = "hello world";
        byte[] data = msg.getBytes();
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(Constants.MAGIC_NUMBER);
        buf.writeInt(ptoNum);
        buf.writeBytes(data);
        ByteBuf input = buf.duplicate();//复制当前对象，复制后的对象与前对象共享缓冲区，且维护自己的独立索引

        Message message = ParseMap.getMessage(ptoNum, data);
        EmbeddedChannel channel = new EmbeddedChannel(new PacketDecoder());

        //验证写数据返回True
        Assert.assertTrue(channel.writeInbound(message));
        Assert.assertTrue(channel.finish());
        //每次读3个数据
//        Assert.assertEquals(buf.readBytes(3), channel.readInbound());
//        Assert.assertEquals(buf.readBytes(3), channel.readInbound());
//        Assert.assertEquals(buf.readBytes(3), channel.readInbound());
//        Assert.assertNull(channel.readInbound());
    }
}
