package core.network.server;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-06
 * Time: 17:49
 */

public interface IServer {
    void bind(int port);
    void shutdownGracefully();
}
