package com.rc.gate.session;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 作者：LizG on 2018/8/26 21:31
 * 描述：客户端连接封装类
 *
 */
@Slf4j
@Data
public class ClientConnection {
    private static final AtomicLong netIdGenerator = new AtomicLong(0);

    private String _userId;
    private long _netId;
    private ChannelHandlerContext _ctx;

    public static AttributeKey<Long> NETID = AttributeKey.valueOf("netId");

    public ClientConnection(ChannelHandlerContext ctx){
        _netId = netIdGenerator.incrementAndGet();
        _ctx = ctx;
        _ctx.attr(ClientConnection.NETID).set(_netId);
    }

    //TODO 扩展session
    public boolean isConnect(){
        return this._ctx.channel().isActive();
    }

    //FIXME 扩展 数据库获取用户信息？
    public void readUserIdFromDB() {}
}
