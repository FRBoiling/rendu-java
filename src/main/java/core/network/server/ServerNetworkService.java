package core.network.server;

import core.network.INetworkServiceBuilder;
import core.network.IService;
import core.network.ServiceState;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Copyright © 2018 四月
 * Boilling blood. All rights reserved.
 *
 * @Prject: ServerCluster-Java
 * @Package: core.network.server
 * @Description: ${todo}
 * @author: Boiling
 * @date: 2018/4/22 0022 12:56
 * @version: V1.0
 */
public class ServerNetworkService implements IService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerNetworkService.class);
    private ServiceState state;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap bootstrap;
    private ServerNetworkServiceBuilder builder;

    int port = 8203;

    ServerNetworkService(final INetworkServiceBuilder serviceBuilder) {
        builder = (ServerNetworkServiceBuilder) serviceBuilder;
        int bossLoopGroupCount = builder.getBossLoopGroupCount();
        int workerLoopGroupCount = builder.getWorkerLoopGroupCount();

        port = builder.getPort();

        bossGroup = new NioEventLoopGroup(bossLoopGroupCount);
        workerGroup = new NioEventLoopGroup(workerLoopGroupCount);

        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_RCVBUF, 128 * 1024);
        bootstrap.childOption(ChannelOption.SO_SNDBUF, 128 * 1024);

        bootstrap.handler(new LoggingHandler(LogLevel.DEBUG));
        bootstrap.childHandler(new ServerSocketChannelInitializer(builder));
    }

    @Override
    public void start() {
        try {
            ChannelFuture f = bootstrap.bind(port);
            f.sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.state = ServiceState.RUNNING;
        LOGGER.info("Server on port:{} is start", port);
    }

    @Override
    public void stop() {
        this.state = ServiceState.STOPPED;
        Future<?> bf = bossGroup.shutdownGracefully();
        Future<?> wf = workerGroup.shutdownGracefully();
        try {
            bf.get(5000, TimeUnit.MILLISECONDS);
            wf.get(5000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            LOGGER.info("Netty服务器关闭失败", e);
        }
        LOGGER.info("Netty Server on port:{} is closed", port);
    }

    @Override
    public ServiceState getState() {
        return this.state;
    }

    @Override
    public boolean isOpened() {
        return state == ServiceState.RUNNING;
    }

    @Override
    public boolean isClosed() {
        return state == ServiceState.STOPPED;
    }

}
