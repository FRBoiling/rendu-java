package core.network.client;

import client.ClientBoostrap;
import core.network.INetworkServiceBuilder;
import core.network.IService;
import core.network.ServiceState;
import core.network.server.ServerNetworkServiceBuilder;
import core.network.server.ServerSocketChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Copyright © 2018 四月
 * Boil blood. All rights reserved.
 *
 * @Prject: ServerCluster-Java
 * @Package: core.network.client
 * @Description: ${todo}
 * @author: Boiling
 * @date: 2018/4/22 0022 13:00
 * @version: V1.0
 */

@Slf4j
public class ClientNetworkService implements IService {
    private ServiceState state;
    private EventLoopGroup workerGroup;
    private Bootstrap bootstrap;
    private ClientNetworkServiceBuilder builder;

    ClientNetworkService(final INetworkServiceBuilder serviceBuilder) {
        builder = (ClientNetworkServiceBuilder) serviceBuilder;
        int workerLoopGroupCount = builder.getWorkerLoopGroupCount();
        workerGroup = new NioEventLoopGroup(workerLoopGroupCount);

        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.SO_RCVBUF, 128 * 1024);
        bootstrap.option(ChannelOption.SO_SNDBUF, 128 * 1024);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000);
//        bootstrap.handler(new LoggingHandler(LogLevel.DEBUG));
        bootstrap.handler(new ClientSocketChannelInitializer(builder));
    }

    @Override
    public void start() {
        try {
            ChannelFuture f = bootstrap.connect(builder.getIp(), builder.getPort());
            f.sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.state = ServiceState.RUNNING;
        log.info("client is start");
    }

    @Override
    public void stop() {
        this.state = ServiceState.STOPPED;
        Future<?> wf = workerGroup.shutdownGracefully();
        try {
            wf.get(5000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.info("Netty客户端关闭失败", e);
        }
        log.info("Netty client stop");
    }

    @Override
    public boolean isOpened() {
        return state == ServiceState.RUNNING;
    }

    @Override
    public boolean isClosed() {
        return state == ServiceState.STOPPED;
    }

    @Override
    public ServiceState getState() {
        return this.state;
    }
}
