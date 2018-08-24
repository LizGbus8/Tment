package protobuf;
/**
 * Buffer工厂
 * @author LizG
 *
 */

import java.nio.ByteOrder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;

public class BufferFactory {
	public static ByteOrder BYTE_ORDER = ByteOrder.BIG_ENDIAN;
	
	private static ByteBufAllocator BUFALLOCATOR = PooledByteBufAllocator.DEFAULT;
	
	/**
	 * 获取一个空的ByteBuf
	 * @return
	 */
	public static ByteBuf getBuffer() {
		ByteBuf buffer = BUFALLOCATOR.heapBuffer();
		buffer = buffer.order(BYTE_ORDER);
		return buffer;
	}
	
	/**
	 * 将字节数组写入数组
	 * @param bytes
	 * @return	带字节数组的ByteBuf
	 */
	public static ByteBuf getBuffer(byte[] bytes) {
		ByteBuf buffer = BUFALLOCATOR.heapBuffer();
		buffer.order(BYTE_ORDER);
		buffer.writeBytes(bytes);
		return buffer;
	}
}
