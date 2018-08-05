package core.network.server.forClientApp;

import core.base.serviceframe.IService;
import core.network.INetworkServiceBuilder;
import core.network.NativeSupport;
import core.network.ServiceState;
import core.network.server.ISocketServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.Future;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-22
 * Time: 13:17
 */
@Slf4j
@Data
public class NetworkService implements IService, ISocketServer {
    private ServiceState state;
    private final EventLoopGroup acceptorGroup;
    private final EventLoopGroup IOGroup;
    private final ServerBootstrap bootstrap;
    private final NetworkServiceBuilder builder;
    protected volatile ByteBufAllocator allocator;

    int port;

    NetworkService(final INetworkServiceBuilder serviceBuilder) {
        builder = (NetworkServiceBuilder) serviceBuilder;
        int acceptorGroupCount = builder.getAcceptorGroupCount();
        int IOGroupCount = builder.getIOGroupCount();

        port = builder.getPort();

//        acceptorGroup = new NioEventLoopGroup(acceptorGroupCount);
//        IOGroup = new NioEventLoopGroup(ioGroupCount);

        ThreadFactory accepterFactory = new DefaultThreadFactory("netty.acceptor.acceptor");
        ThreadFactory IOFactory = new DefaultThreadFactory("netty.acceptor.io");
        acceptorGroup = initEventLoopGroup(acceptorGroupCount,accepterFactory);
        IOGroup = initEventLoopGroup(IOGroupCount,IOFactory);

        bootstrap = new ServerBootstrap();
        bootstrap.group(acceptorGroup, IOGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        InitOption();
        bootstrap.childHandler(new HandlerInitializer(this));
    }


    private EventLoopGroup initEventLoopGroup(int threadCount, ThreadFactory bossFactory) {
        return NativeSupport.isSupportNativeET() ? new EpollEventLoopGroup(threadCount, bossFactory) : new NioEventLoopGroup(threadCount, bossFactory);
    }


    private void InitOption() {
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_RCVBUF, 32 * 1024);
        bootstrap.childOption(ChannelOption.SO_SNDBUF, 64 * 1024);
        bootstrap.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(16 * 1024, 256 * 1024));
    }

    @Override
    public void start() {
        bind(port);
    }

    @Override
    public void stop() {
        this.state = ServiceState.STOPPED;
        shutdownGracefully();
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

    @Override
    public void bind(int port) {
        try {
            ChannelFuture f = bootstrap.bind(port);
            f.addListener(new BindListener(this));
            f.sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(String[] args) {

    }

    @Override
    public void update(long dt) {
    }

    @Override
    public void shutdownGracefully() {
        Future<?> bf = acceptorGroup.shutdownGracefully();
        Future<?> wf = IOGroup.shutdownGracefully();
        try {
            bf.get(5000, TimeUnit.MILLISECONDS);
            wf.get(5000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.info("Netty服务器关闭失败", e);
        }
        log.info("Netty Server on listenPort:{} is closed", port);
    }
}
