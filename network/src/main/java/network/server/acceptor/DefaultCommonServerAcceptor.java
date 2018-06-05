package network.server.acceptor;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import network.codec.*;
import network.common.NativeSupport;
import network.common.NettyEvent;
import network.common.NettyEventType;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-04
 * Time: 13:13
 */
@Slf4j
public class DefaultCommonServerAcceptor extends AbstractAcceptor {

    private final IChannelEventListener channelEventListener;
    //acceptor的trigger     //因为我们在client端设置了每隔30s会发送一个心跳包过来，如果60s都没有收到心跳，则说明链路发生了问题
    private final AcceptorIdleStateTrigger idleStateTrigger = new AcceptorIdleStateTrigger();
    //封包
    private final MessageWriter writer = new MessageWriter();
    //message的编码器
    private final MessageEncoder encoder = new MessageEncoder();
    //Ack的编码器
    private final AcknowledgeEncoder ackEncoder = new AcknowledgeEncoder();
    //连接管理
    private final NettyConnectManageHandler nettyConnectManageHandler = new NettyConnectManageHandler(this);
    //SimpleChannelInboundHandler类型的handler只处理@{link Message}类型的数据
    private final AcceptorMessageHandler messageHandler = new AcceptorMessageHandler();

    public DefaultCommonServerAcceptor(int port, IChannelEventListener channelEventListener) {
        super(new InetSocketAddress(port));
        this.Init();
        this.channelEventListener = channelEventListener;
    }

    public void Init() {
        super.init();
        bootstrap().option(ChannelOption.SO_BACKLOG, 32768)
                .option(ChannelOption.SO_REUSEADDR, true);

        bootstrap().childOption(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.ALLOW_HALF_CLOSURE, false);
    }

    @Override
    protected IChannelEventListener getChannelEventListener() {
        return channelEventListener;
    }

    @Override
    protected EventLoopGroup initEventLoopGroup(int threadCount, ThreadFactory threadFactory) {
        return NativeSupport.isSupportNativeET() ? new EpollEventLoopGroup(threadCount, threadFactory) : new NioEventLoopGroup(threadCount, threadFactory);
    }

    @Override
    public ChannelFuture bind(SocketAddress localAddress) {
        ServerBootstrap boot = bootstrap();
        boot.channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(
                                //每隔60s的时间内如果没有接受到任何的read事件的话，则会触发userEventTriggered事件，并指定IdleState的类型为READER_IDLE
                                new IdleStateHandler(60, 0, 0, TimeUnit.SECONDS),
                                idleStateTrigger,
                                writer,
                                encoder,
                                ackEncoder,
                                //拆包
                                new MessageReader(),
                                //message的解码器
                                new MessageDecoder(),
                                nettyConnectManageHandler,
                                messageHandler
                             );
                    }
                });
        return boot.bind(localAddress);
    }

}
