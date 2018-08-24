package protobuf;


import protobuf.analysis.ParseMap;
import protobuf.generate.cli2srv.chat.Chat;
import protobuf.generate.cli2srv.login.Auth;
import protobuf.generate.internal.Internal;

import java.io.IOException;

/**
 * 作者：LizG on 2018/8/24 20:04
 * 描述：
 */
public class ParseRegistryMap {
    /** 内部协议 */
    public static final int GTRANSFER = 1000;
    public static final int HANDSHAKE = 1001;

    /** 登录 */
    public static final int CLOGIN = 1002;

    public static final int CREGISTER = 1003;
    public static final int SRESPONSE = 1004;

    public static final int CPRIVATECHAT = 1005;
    public static final int SPRIVATECHAT = 1006;

    public static void initRegistry() throws IOException{
        /** 内部传输协议用 */
        ParseMap.register(GTRANSFER, Internal.GTransfer::parseFrom, Internal.GTransfer.class);
        ParseMap.register(HANDSHAKE, Internal.Handshake::parseFrom, Internal.Handshake.class);

        ParseMap.register(CLOGIN, Auth.CLogin::parseFrom, Auth.CLogin.class);

        ParseMap.register(CREGISTER, Auth.CRegister::parseFrom, Auth.CRegister.class);
        ParseMap.register(SRESPONSE, Auth.SResponse::parseFrom, Auth.SResponse.class);

        ParseMap.register(CPRIVATECHAT, Chat.CPrivateChat::parseFrom, Chat.CPrivateChat.class);
        ParseMap.register(SPRIVATECHAT, Chat.SPrivateChat::parseFrom, Chat.SPrivateChat.class);
    }
}
