package protobuf.codec;

import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import protobuf.analysis.ParseMap;
import protobuf.utils.BufUtils;

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

        byte[] bytes = msg.toByteArray();
        int ptoNum = ParseMap.getPtoNum(msg);
        int length = bytes.length;

        //TODO 加密

        ByteBuf buffer = BufUtils.getBuffer();
        /** 写魔数 */
        buffer.writeInt(Constants.MAGIC_NUMBER);

        /** 写协议号 */
        buffer.writeInt(ptoNum);
        /** 写长度 */
        buffer.writeInt(length);
        /** 写数据 */
        buffer.writeBytes(bytes);

        out.writeBytes(buffer);

        log.info("GateServer send message remoteAdress：{}，content_length：{}，ptoNum：{}",ctx.channel().remoteAddress(),length,ptoNum);
    }
}
