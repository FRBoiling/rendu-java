package network.client.connector;

import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultMessageSizeEstimator;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ThreadFactory;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-02
 * Time: 18:00
 */

public class AbstractConnector extends AbstractNettyConnector {

    protected void init() {
        super.init();

    }

    @Override
    protected EventLoopGroup initEventLoopGroup(int nthread, ThreadFactory bossFactory) {
        return null;
    }

    @Override
    public Channel connect(int port, String host) {
        return null;
    }


}
