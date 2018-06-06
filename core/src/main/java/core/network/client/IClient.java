package core.network.client;

import io.netty.channel.Channel;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-06
 * Time: 17:49
 */

public interface IClient {
    void connect(String host,int port);
    void shutdownGracefully();
}
