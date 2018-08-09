package core.network.acceptor.http;

import core.base.serviceframe.IService;
import core.network.IChannelHandlerHolder;
import core.network.acceptor.AcceptorIdleStateTrigger;
import core.network.acceptor.AcceptorMessageExecutor;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Copyright © 2018 四月
 * Boil blood. All rights reserved.
 *
 * Project: ServerCluster-Java
 * Package: core.network.acceptor
 * Description: ${todo}
 * author: Boiling
 * date: 2018/4/22 0022 15:28
 * version: V1.0
 */

@Slf4j
public class HttpHandlerInitializer extends ChannelInitializer<SocketChannel> implements IChannelHandlerHolder {
    private IService service;
    //acceptor的trigger     //因为我们在client端设置了每隔30s会发送一个心跳包过来，如果60s都没有收到心跳，则说明链路发生了问题
    private final AcceptorIdleStateTrigger idleStateTrigger = new AcceptorIdleStateTrigger();
    //
    private AcceptorMessageExecutor messageExecutor;
    //
    ChannelHandler[] handlers;

    HttpHandlerInitializer(IService service) {
        this.service = service;
        messageExecutor = new AcceptorMessageExecutor(service);
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pip = channel.pipeline();
        handlers = new ChannelHandler[]{
                //每隔60s的时间内如果没有接受到任何的read事件的话，则会触发userEventTriggered事件，并指定IdleState的类型为READER_IDLE
                new IdleStateHandler(160, 0, 0, TimeUnit.SECONDS),
                idleStateTrigger,
                new HttpResponseEncoder(),
                new HttpRequestDecoder(),
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
