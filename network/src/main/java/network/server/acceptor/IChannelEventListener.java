package network.server.acceptor;

import io.netty.channel.Channel;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-02
 * Time: 14:32
 */

public interface IChannelEventListener {

    void onChannelConnect(final String remoteAddress, final Channel channel);

    void onChannelClose(final String remoteAddress, final Channel channel);

    void onChannelException(final String remoteAddress, final Channel channel);

    void onChannelIdle(final String remoteAddress, final Channel channel);
}
