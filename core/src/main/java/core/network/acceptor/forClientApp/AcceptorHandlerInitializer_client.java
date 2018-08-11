package core.network.acceptor.forClientApp;

import core.network.IChannelHandlerHolder;
import core.network.acceptor.AcceptorMessageExecutor;
import core.network.codec.forClientApp.DecoderHandler;
import core.network.codec.forClientApp.ReaderHandler;
import core.network.codec.forClientApp.WriterHandler;
import core.network.acceptor.AcceptorIdleStateTrigger;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Copyright © 2018 四月
 * Boil blood. All rights reserved.
 * <p>
 * Project: ServerCluster-Java
 * Package: core.network.acceptor
 * Description: ${todo}
 * author: Boiling
 * date: 2018/4/22 0022 15:28
 * version: V1.0
 */

@Slf4j
public class AcceptorHandlerInitializer_client extends ChannelInitializer<SocketChannel> implements IChannelHandlerHolder {
    //acceptor的trigger     //因为我们在client端设置了每隔30s会发送一个心跳包过来，如果60s都没有收到心跳，则说明链路发生了问题
    private final AcceptorIdleStateTrigger idleStateTrigger = new AcceptorIdleStateTrigger();
    //封包
    private final WriterHandler writer = new WriterHandler();
    //
    private AcceptorMessageExecutor messageExecutor;
    //
    ChannelHandler[] handlers;

    AcceptorHandlerInitializer_client(AcceptorNetworkServiceBuilder_client builder) {
        messageExecutor = new AcceptorMessageExecutor(builder);
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pip = channel.pipeline();
        handlers = new ChannelHandler[]{
                //每隔60s的时间内如果没有接受到任何的read事件的话，则会触发userEventTriggered事件，并指定IdleState的类型为READER_IDLE
                new IdleStateHandler(160, 0, 0, TimeUnit.SECONDS),
                idleStateTrigger,
                writer,
                //拆包
                new ReaderHandler(),
                //message的解码器
                new DecoderHandler(),
                messageExecutor
        };
        pip.addLast(handlers);
//        log.info("------------->initChannel");
    }

    @Override
    public ChannelHandler[] handlers() {
        return handlers;
    }
}
