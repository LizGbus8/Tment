package protobuf.utils;

import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import protobuf.ParseRegistryMap;
import protobuf.analysis.ParseMap;
import protobuf.codec.Constants;
import protobuf.generate.internal.Internal;

import java.nio.ByteOrder;

/**
 * 作者：LizG on 2018/8/24 23:34
 * 描述：Buffer相关的工具类
 */
public class BufUtils {
    /** 自己存储次序 大端小端 */
    public static ByteOrder BYTE_ORDER = ByteOrder.BIG_ENDIAN;

    private static ByteBufAllocator ALLOCATOR = new UnpooledByteBufAllocator(false);

    public static ByteBuf getBuffer(){
        ByteBuf buffer = ALLOCATOR.buffer();
        buffer.order(BYTE_ORDER);
        return buffer;
    }

    public static ByteBuf getBuffer(byte[] bytes){
        ByteBuf buffer = ALLOCATOR.buffer();
        buffer.order(BYTE_ORDER);
        buffer.writeBytes(bytes);
        return buffer;
    }

    public static ByteBuf pack2Server(Message msg, int ptoNum, long netId, Internal.Dest dest, String userId) {
        Internal.GTransfer.Builder gtf = Internal.GTransfer.newBuilder();
        gtf.setPtoNum(ptoNum);
        gtf.setMsg(msg.toByteString());
        gtf.setNetId(netId);
        gtf.setDest(dest);
        gtf.setUserId(userId);

        byte[] bytes = gtf.build().toByteArray();
        int length =bytes.length;
        int gtfNum = ParseRegistryMap.GTRANSFER;

        ByteBuf buf = Unpooled.buffer(8 + length);
        buf.writeInt(Constants.MAGIC_NUMBER);
        buf.writeInt(gtfNum);     //传输协议的协议号
        buf.writeInt(length);
        buf.writeBytes(bytes);

        return buf;
    }

    public static ByteBuf pack2Client(Message msg) {
        byte[] bytes = msg.toByteArray();
        int length =bytes.length;
        int ptoNum = ParseMap.getPtoNum(msg);

        ByteBuf buf = Unpooled.buffer(8 + length);
        buf.writeInt(Constants.MAGIC_NUMBER);
        buf.writeInt(ptoNum);
        buf.writeInt(length);
        buf.writeBytes(bytes);

        return buf;
    }

    public static ByteBuf pack2Server(Message msg, int ptoNum, Internal.Dest dest, String userId) {
        Internal.GTransfer.Builder gtf = Internal.GTransfer.newBuilder();
        gtf.setPtoNum(ptoNum);
        gtf.setMsg(msg.toByteString());
        gtf.setDest(dest);
        gtf.setUserId(userId);

        byte[] bytes = gtf.build().toByteArray();
        int length =bytes.length;
        int gtfNum = ParseRegistryMap.GTRANSFER;

        ByteBuf buf = Unpooled.buffer(8 + length);
        buf.writeInt(Constants.MAGIC_NUMBER);
        buf.writeInt(gtfNum);     //传输协议的协议号
        buf.writeInt(length);
        buf.writeBytes(bytes);

        return buf;
    }

}
