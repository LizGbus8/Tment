package com.rc.gate.session;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 作者：LizG on 2018/8/26 22:05
 * 描述：客户端连接Session域
 */
@Slf4j
public class ClientConnectionMap {

    /** 保存所有连接到Gateway上的客户端连接 */
    public static ConcurrentHashMap<Long,ClientConnection> clientConnections = new ConcurrentHashMap<>();

    /** user_id 到 net_id 的映射*/
    private static ConcurrentHashMap<String,Long> userId2netIdMap = new ConcurrentHashMap<>();

    public ClientConnection getClientConn(ChannelHandlerContext c) {
        Long netId = c.attr(ClientConnection.NETID).get();
        ClientConnection conn = ClientConnectionMap.clientConnections.get(netId);
        if (conn != null) {
            return conn;
        } else {
            log.error("ClientConenction not found in allClientMap, netId: {}", netId);
        }
        return null;
    }

    public static ClientConnection getClientConn(long netId) {
        ClientConnection conn = clientConnections.get(netId);
        if (conn != null)
            return conn;
        else {
            log.error("ClientConenction not found in allClientMap, netId: {}", netId);
        }
        return null;
    }

    //TODO 2018-8-26 
    public void addClientConn(ChannelHandlerContext c) {
        ClientConnection clientConnection = new ClientConnection(c);

        long netId = clientConnection.get_netId();

        if (ClientConnectionMap.clientConnections.putIfAbsent(netId, clientConnection) != null) {
            log.error("Duplicated netid");
        }
    }

    public void removeClientConn(ChannelHandlerContext c) {
        ClientConnection clientConn = getClientConn(c);
        if (clientConn != null) {
            long netId = clientConn.get_netId();
            ClientConnectionMap.clientConnections.remove(netId);

            String userId = clientConn.get_userId();
            ClientConnectionMap.userId2netIdMap.remove(userId);
        } else {
            log.error("ChannelHandlerContext not found in clientConnections,netId is {}", c.channel());
        }
    }

    public void registerUserId(String userId, Long netId) {
        if (userId2netIdMap.putIfAbsent(userId,netId) == null){
            ClientConnection clientConn = ClientConnectionMap.getClientConn(netId);
            if (clientConn != null){
                clientConn.set_userId(userId);
            }else {
                log.error("clientConnection is null,netId is {}",netId);
                return;
            }
        }else {
            log.info("user_id:{} has registered in userId2netIdMap",userId);
        }
    }

    public void cancleRegisterUserId(String userId) {
        if (userId2netIdMap.remove(userId) == null) {
            log.error("user_id:{} have not registered in userId2netIdMap",userId);
        }
    }

    public static Long userId2NetId(String userId){
        Long netId = userId2netIdMap.get(userId);
        if (netId != null) {
            return netId;
        }else {
            log.error("userId:{} not exist in userId2netIdMap",userId);
        }
        return null;
    }
}
