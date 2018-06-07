package core.network.client;

import core.base.exception.ConnectFailedException;
import core.network.INetworkServiceBuilder;
import core.network.IService;
import core.network.ServiceState;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.HashedWheelTimer;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.PlatformDependent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.SECONDS;

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
@Data
public class ClientNetworkService implements IService {
    private  ServiceState state;
    private final EventLoopGroup workerGroup;
    private final Bootstrap bootstrap;
    private final Object bootstrapLock;
    private final ClientNetworkServiceBuilder builder;
    private final ClientSocketChannelInitializer channelInitializer;
    protected volatile ByteBufAllocator allocator;

    ClientNetworkService(final INetworkServiceBuilder serviceBuilder) {
        builder = (ClientNetworkServiceBuilder) serviceBuilder;
        int workerLoopGroupCount = builder.getWorkerLoopGroupCount();
        workerGroup = new NioEventLoopGroup(workerLoopGroupCount);
        channelInitializer = new ClientSocketChannelInitializer(builder.getConsumer(),builder.getListener());

        bootstrapLock =new Object();
        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        InitOption2();

//        bootstrap.handler(new LoggingHandler(LogLevel.DEBUG));
    }

    public void InitOption1() {
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.SO_RCVBUF, 128 * 1024);
        bootstrap.option(ChannelOption.SO_SNDBUF, 128 * 1024);
        bootstrap.option(ChannelOption.WRITE_BUFFER_WATER_MARK ,new WriteBufferWaterMark(64*1024,1024* 1024));
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 15000);
    }
    public void InitOption2()
    {
        bootstrap.option(ChannelOption.ALLOCATOR, allocator)
                .option(ChannelOption.MESSAGE_SIZE_ESTIMATOR, DefaultMessageSizeEstimator.DEFAULT)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) SECONDS.toMillis(3))

                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.ALLOW_HALF_CLOSURE, false);
        //使用池化的directBuffer
        allocator = new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
        bootstrap.option(ChannelOption.ALLOCATOR, allocator);
    }

    @Override
    public void start(){
        try {
            channelInitializer.getWatchdog().init(bootstrap,builder.getIp(), builder.getPort());
            channelInitializer.getWatchdog().setReconnect(true);
            ChannelFuture f;
            synchronized (bootstrapLock) {
                bootstrap.handler(channelInitializer);
                f = bootstrap.connect(builder.getIp(), builder.getPort());
                f.addListener(new ClientConnectionListener(this));
            }
            f.sync();
        } catch (Exception e) {
//            throw new RuntimeException(e);
//            throw new ConnectFailedException("connects to [" + builder.getIp() + ":"+builder.getPort()+"] fails", e);
              throw new ConnectFailedException("connects to [" + builder.getIp() + ":"+builder.getPort()+"] fails:"+e.getMessage());
        }
    }

    @Override
    public void stop() {
        this.state = ServiceState.STOPPED;
        Future<?> wf = workerGroup.shutdownGracefully();
        try {
            wf.get(5000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("Netty client stop failed ", e);
        }
        log.info("Netty client stop!");
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
