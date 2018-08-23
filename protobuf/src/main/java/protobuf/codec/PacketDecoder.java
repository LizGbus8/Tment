package protobuf.codec;

import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import protobuf.analysis.ParseMap;

import java.util.List;

/**
 * 作者：LizG on 2018/8/23 15:30
 * 描述：数据包解码器
 *
 * <pre>
 * 数据包格式
 * +——----——+——----——+——----——+——-----——+
 * |  魔数	| 协议号  |  长度   |  数据   |
 * +——----——+——----——+——----——+——-----——+
 * </pre>
 */
@Slf4j
public class PacketDecoder extends ByteToMessageDecoder {

    /**
     * 包最小长度
     */
    public static int PACKET_LENTH = 4 + 4 + 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {

        while (true) {
            /** 数据包小于最小长度，退出循环 */
            if (buffer.readableBytes() < PACKET_LENTH) {
                break;
            }

            /** 数据包开始位置 */
            int beginIndex;

            /** 按位读取，找出魔数位置 */
            while (true) {
                beginIndex = buffer.readerIndex();//记录魔数位置
                buffer.markReaderIndex();

                if (buffer.readInt() == Constants.MAGIC_NUMBER) {
                    break;
                }

                buffer.resetReaderIndex();
                buffer.readByte();
                if (buffer.readableBytes() <= PACKET_LENTH) {
                    return;
                }
            }

            /** 服务号 */
            int ptoNum = buffer.readInt();

            /** 数据长度 */
            int length = buffer.readInt();

            if (length < 0) {
                ctx.close();
                return;
            }

            if (buffer.readableBytes() < length) {
                break;
            }

            try {
                /** byte --> message */
                byte[] data = new byte[length];
                buffer.readBytes(data);

                Message message = ParseMap.getMessage(ptoNum, data);
                out.add(message);
                log.info("GateServer receive message,message length:{},ptoNum:{}", length, ptoNum);
            } catch (Exception e) {
                log.error(ctx.channel().remoteAddress() + ",decode fail!");
            }
        }

        return;
    }
}
