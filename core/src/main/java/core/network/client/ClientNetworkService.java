package core.network.client;

import core.base.exception.ConnectFailedException;
import core.network.INetworkServiceBuilder;
import core.base.serviceframe.IService;
import core.network.NativeSupport;
import core.network.ServiceState;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.PlatformDependent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

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
public class ClientNetworkService implements IService ,ISocketClient {
    private  ServiceState state;
    private final EventLoopGroup IOGroup;
    private final Bootstrap bootstrap;
    private final Object bootstrapLock;
    private final ClientNetworkServiceBuilder builder;
    private final ClientSocketChannelInitializer channelInitializer;
    protected volatile ByteBufAllocator allocator;


    ClientNetworkService(final INetworkServiceBuilder serviceBuilder) {
        builder = (ClientNetworkServiceBuilder) serviceBuilder;
        int IOLoopGroupCount = builder.getWorkerLoopGroupCount();

//      IOGroup = new NioEventLoopGroup(IOLoopGroupCount);

        ThreadFactory IOFactory = new DefaultThreadFactory("client.connector.io");
        IOGroup = initEventLoopGroup(IOLoopGroupCount,IOFactory);
        channelInitializer = new ClientSocketChannelInitializer(builder.getConsumer(),builder.getListener());

        bootstrapLock =new Object();
        bootstrap = new Bootstrap();
        bootstrap.group(IOGroup);
        bootstrap.channel(NioSocketChannel.class);
        InitOption1();

//        bootstrap.handler(new LoggingHandler(LogLevel.DEBUG));
    }

    protected EventLoopGroup initEventLoopGroup(int threadCount, ThreadFactory workerFactory) {
        return NativeSupport.isSupportNativeET() ? new EpollEventLoopGroup(threadCount, workerFactory) : new NioEventLoopGroup(threadCount, workerFactory);
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
    public void init(String[] args) {

    }

    @Override
    public void update(long dt) {

    }

    @Override
    public void start(){
        connect(builder.getIp(),builder.getPort());
    }

    @Override
    public void stop() {
        this.state = ServiceState.STOPPED;
        shutdownGracefully();
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

    @Override
    public void connect(String host, int port) {
        try {
            channelInitializer.getWatchdog().init(bootstrap,host, port);
            channelInitializer.getWatchdog().setReconnect(true);
            ChannelFuture f;
            synchronized (bootstrapLock) {
                bootstrap.handler(channelInitializer);
                f = bootstrap.connect(host, port);
                f.addListener(new ClientConnectionListener(this));
            }
//           f.sync();
        } catch (Exception e) {
//            throw new RuntimeException(e);
//            throw new ConnectFailedException("connects to [" + builder.getIp() + ":"+builder.getPort()+"] fails", e);
//            throw new ConnectFailedException("connects to [" + builder.getIp() + ":"+builder.getPort()+"] fails:"+e.getMessage());
        }
    }

    @Override
    public void shutdownGracefully() {
        Future<?> wf = IOGroup.shutdownGracefully();
        try {
            wf.get(5000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("Netty client stop failed ", e);
        }
        log.info("Netty client stop!");
    }
}
