package network.client.connector;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;
import network.codec.*;
import network.common.NativeSupport;
import network.exception.ConnectFailedException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-04
 * Time: 15:53
 */

public class DefaultCommonClientConnector extends AbstractConnector {

    protected final HashedWheelTimer timer = new HashedWheelTimer(new ThreadFactory() {
        private AtomicInteger threadIndex = new AtomicInteger(0);
        public Thread newThread(Runnable r) {
            return new Thread(r, "NettyClientConnectorExecutor_" + this.threadIndex.incrementAndGet());
        }
    });

    //实现userEventTriggered方法，并在state是WRITER_IDLE的时候发送一个心跳包到sever端，告诉server端我还活着
    private final ConnectorIdleStateTrigger idleStateTrigger = new ConnectorIdleStateTrigger();
    //封包
    private final MessageWriter writer = new MessageWriter();
    //message的编码器
    private final MessageEncoder encoder = new MessageEncoder();
    //Ack的编码器
    private final AcknowledgeEncoder ackEncoder = new AcknowledgeEncoder();

    //SimpleChannelInboundHandler类型的handler只处理@{link Message}类型的数据
    private final ConnectorMessageHandler messageHandler = new ConnectorMessageHandler();

    //每个连接维护一个channel
    private volatile Channel channel;

    public DefaultCommonClientConnector() {
        this.Init();
    }

    public void Init() {
        super.init();

        bootstrap().option(ChannelOption.MESSAGE_SIZE_ESTIMATOR, DefaultMessageSizeEstimator.DEFAULT)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) SECONDS.toMillis(3));

        bootstrap().option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.ALLOW_HALF_CLOSURE, false);
    }

    @Override
    protected EventLoopGroup initEventLoopGroup(int threadCount, ThreadFactory threadFactory) {
        return NativeSupport.isSupportNativeET() ? new EpollEventLoopGroup(threadCount, threadFactory) : new NioEventLoopGroup(threadCount, threadFactory);
    }
    @Override
    public Channel connect(int port, String host) {
        Bootstrap boot = bootstrap();
        boot.channel(NioSocketChannel.class);
        // 重连watchdog
        final ConnectorWatchdog watchdog = new ConnectorWatchdog(boot, timer, port,host) {

            public ChannelHandler[] handlers() {
                return new ChannelHandler[] {
                        //将自己[ConnectorWatchdog]装载到handler链中，当链路断掉之后，会触发ConnectionWatchdog #channelInActive方法
                        this,
                        //每隔30s的时间触发一次userEventTriggered的方法，并且指定IdleState的状态位是WRITER_IDLE
                        new IdleStateHandler(0, 30, 0, TimeUnit.SECONDS),
                        idleStateTrigger,
                        writer,
                        encoder,
                        //拆包
                        new MessageReader(),
                        //message的解码器
                        new MessageDecoder(),
                        ackEncoder,
                        messageHandler
                };
            }};
        watchdog.setReconnect(true);

        try {
            ChannelFuture future;
            synchronized (bootstrapLock()) {
                boot.handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(watchdog.handlers());
                    }
                });

                future = boot.connect("127.0.0.1", 20011);
            }
            future.sync();
            channel = future.channel();
        } catch (Throwable t) {
            throw new ConnectFailedException("connects to [" + host + ":"+port+"] fails", t);
        }
        return channel;
    }
}
