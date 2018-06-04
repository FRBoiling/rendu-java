package network.server.acceptor;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.internal.PlatformDependent;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;
import java.util.concurrent.ThreadFactory;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-02
 * Time: 13:25
 */
@Slf4j
public abstract class AbstractNettyAcceptor implements IAcceptor {

    protected final SocketAddress localAddress;

    //netty base element
    private ServerBootstrap bootstrap;
    private EventLoopGroup acceptorGroup;
    private EventLoopGroup ioGroup;
    private int ioGroupCount;
    protected volatile ByteBufAllocator allocator;
    /**
     * 可用处理器核数
     */
    public static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    public AbstractNettyAcceptor(SocketAddress localAddress) {
        this(localAddress, AVAILABLE_PROCESSORS << 1);
    }

    private AbstractNettyAcceptor(SocketAddress localAddress, int ioGroupCount) {
        this.localAddress = localAddress;
        this.ioGroupCount = ioGroupCount;
    }

    protected abstract EventLoopGroup initEventLoopGroup(int nthread, ThreadFactory bossFactory);

    public abstract ChannelFuture bind(SocketAddress localAddress);

    protected void init(){
        ThreadFactory acceptorGroupFactory = new DefaultThreadFactory("netty.acceptor.acceptor");
        ThreadFactory ioGroupFactory = new DefaultThreadFactory("netty.acceptor.io");

        acceptorGroup = initEventLoopGroup(1, acceptorGroupFactory);

        ioGroup = initEventLoopGroup(ioGroupCount, ioGroupFactory);
        //使用池化的directBuffer
        /**
         * 一般高性能的场景下,使用的堆外内存，也就是直接内存，使用堆外内存的好处就是减少内存的拷贝，和上下文的切换，缺点是
         * 堆外内存处理的不好容易发生堆外内存OOM
         * 当然也要看当前的JVM是否只是使用堆外内存，换而言之就是是否能够获取到Unsafe对象#PlatformDependent.directBufferPreferred()
         */
        allocator = new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
        //create && group
        bootstrap= new ServerBootstrap().group(acceptorGroup, ioGroup);
        //ByteBufAllocator 配置
        bootstrap.childOption(ChannelOption.ALLOCATOR, allocator);

    }

    protected ServerBootstrap bootstrap() {
        return bootstrap;
    }

    public void start(boolean sync) throws InterruptedException {
        ChannelFuture future = bind(localAddress).sync();
        log.info("netty acceptor server start");
        if (sync) {
            future.channel().closeFuture().sync();
            log.info("netty acceptor server start");
        }
    }

    @Override
    public SocketAddress localAddress() {
        return localAddress;
    }

    @Override
    public void start() throws InterruptedException {
        this.start(true);
    }

    @Override
    public void shutdownGracefully() {
        acceptorGroup.shutdownGracefully().awaitUninterruptibly();
        ioGroup.shutdownGracefully().awaitUninterruptibly();
    }


}
