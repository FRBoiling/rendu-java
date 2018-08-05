package core.network.client;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-06
 * Time: 17:49
 */

public interface ISocketClient {
    void connect(String host,int port);
    void shutdownGracefully();
}
