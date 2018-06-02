package network.client.connector;

import io.netty.channel.Channel;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-02
 * Time: 12:13
 */

public interface IConnector {

    Channel connect(int port, String host);

    void shutdownGracefully();
}
