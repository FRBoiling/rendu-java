package core.network.server.forClientApp;

import core.base.serviceframe.IService;
import core.network.IChannelHandlerHolder;
import core.network.codec.forClientApp.DecoderHandler;
import core.network.codec.forClientApp.ReaderHandler;
import core.network.codec.forClientApp.WriterHandler;
import core.network.server.ServerIdleStateTrigger;
import core.network.server.ServerMessageExecutor;
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
 *
 * @Prject: ServerCluster-Java
 * @Package: core.network.server
 * @Description: ${todo}
 * @author: Boiling
 * @date: 2018/4/22 0022 15:28
 * @version: V1.0
 */

@Slf4j
public class HandlerInitializer extends ChannelInitializer<SocketChannel> implements IChannelHandlerHolder {
    private IService service;
    //acceptor的trigger     //因为我们在client端设置了每隔30s会发送一个心跳包过来，如果60s都没有收到心跳，则说明链路发生了问题
    private final ServerIdleStateTrigger idleStateTrigger = new ServerIdleStateTrigger();
    //封包
    private final WriterHandler writer = new WriterHandler();
    //
    private MessageExecutor messageExecutor;
    //
    ChannelHandler[] handlers;

    HandlerInitializer(IService service) {
        this.service = service;
        messageExecutor = new MessageExecutor(service);
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
