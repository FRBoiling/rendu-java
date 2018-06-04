package network.client.connector;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.internal.PlatformDependent;
import lombok.Getter;

import java.util.concurrent.ThreadFactory;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-02
 * Time: 17:53
 */
@Getter
public abstract class AbstractNettyConnector implements IConnector {
    public static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private Object bootstrapLock;
    private Bootstrap bootstrap;
    private EventLoopGroup ioGroup;
    private int ioGroupCount;

    protected volatile ByteBufAllocator allocator;

    public AbstractNettyConnector() {
        this(AVAILABLE_PROCESSORS << 1);
    }

    private AbstractNettyConnector(int ioGroupCount) {
        this.ioGroupCount = ioGroupCount;
    }

    protected void init() {
        ThreadFactory ioGroupFactory = new DefaultThreadFactory("client.connector");
        ioGroup = initEventLoopGroup(ioGroupCount, ioGroupFactory);


        //使用池化的directBuffer
        /**
         * 一般高性能的场景下,使用的堆外内存，也就是直接内存，使用堆外内存的好处就是减少内存的拷贝，和上下文的切换，缺点是
         * 堆外内存处理的不好容易发生堆外内存OOM
         * 当然也要看当前的JVM是否只是使用堆外内存，换而言之就是是否能够获取到Unsafe对象#PlatformDependent.directBufferPreferred()
         */
        allocator = new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
        bootstrapLock = new Object();
        bootstrap = new Bootstrap().group(ioGroup);
        //ByteBufAllocator 配置
        bootstrap.option(ChannelOption.ALLOCATOR, allocator);
    }

    protected abstract EventLoopGroup initEventLoopGroup(int ioGroupCount, ThreadFactory bossFactory);

    protected Bootstrap bootstrap() {
        return bootstrap;
    }

    protected Object bootstrapLock()
    {
        return bootstrapLock;
    }

    @Override
    public void shutdownGracefully() {
        ioGroup.shutdownGracefully();
    }
}
