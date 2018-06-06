package core.network.server;

import core.network.INetworkServiceBuilder;
import core.network.IService;
import core.network.ServiceState;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

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
public class ServerNetworkService implements IService ,IServer{
    private ServiceState state;
    private EventLoopGroup acceptorGroup;
    private EventLoopGroup IOGroup;
    private ServerBootstrap bootstrap;
    private ServerNetworkServiceBuilder builder;

    int port = 8203;

    ServerNetworkService(final INetworkServiceBuilder serviceBuilder) {
        builder = (ServerNetworkServiceBuilder) serviceBuilder;
        int acceptorGroupCount = builder.getAcceptorGroupCount();
        int ioGroupCount = builder.getIOGroupCount();

        port = builder.getPort();

        acceptorGroup = new NioEventLoopGroup(acceptorGroupCount);
        IOGroup = new NioEventLoopGroup(ioGroupCount);

        bootstrap = new ServerBootstrap();
        bootstrap.group(acceptorGroup, IOGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_RCVBUF, 128 * 1024);
        bootstrap.childOption(ChannelOption.SO_SNDBUF, 128 * 1024);
        bootstrap.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK ,new WriteBufferWaterMark(64*1024,1024* 1024));
//        bootstrap.handler(new LoggingHandler(LogLevel.DEBUG));
        bootstrap.childHandler(new ServerSocketChannelInitializer(this));
    }

    @Override
    public void start() {
        try {
            ChannelFuture f = bootstrap.bind(port);
            f.addListener(new ServerBindListener(this));
            f.sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        this.state = ServiceState.STOPPED;
        Future<?> bf = acceptorGroup.shutdownGracefully();
        Future<?> wf = IOGroup.shutdownGracefully();
        try {
            bf.get(5000, TimeUnit.MILLISECONDS);
            wf.get(5000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.info("Netty服务器关闭失败", e);
        }
        log.info("Netty Server on port:{} is closed", port);
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

    }

    @Override
    public void shutdownGracefully() {

    }
}