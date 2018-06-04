package network.client.connector;

import io.netty.channel.ChannelHandler;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-04
 * Time: 16:12
 */

public interface IChannelHandlerHolder {
    ChannelHandler[] handlers();
}
