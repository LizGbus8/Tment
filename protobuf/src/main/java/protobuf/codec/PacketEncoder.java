package protobuf.codec;

import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 作者：LizG on 2018/8/24 22:21
 * 描述：数据包编码器
 *
 * <pre>
 * 数据包格式
 * +——----——+——----——+——----——+——-----——+
 * |  魔数	| 协议号  |  长度   |  数据   |
 * +——----——+——----——+——----——+——-----——+
 * </pre>
 */
@Slf4j
public class PacketEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        
    }
}
