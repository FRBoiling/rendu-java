package network.server.acceptor;

import io.netty.channel.ChannelFuture;

import java.net.SocketAddress;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-02
 * Time: 12:13
 */

public interface IAcceptor {
    SocketAddress localAddress();

    void start() throws InterruptedException;

    void shutdownGracefully();

    ChannelFuture bind(SocketAddress localAddress);
}
