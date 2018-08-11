package core.network.acceptor;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-06
 * Time: 17:49
 */

public interface ISocketAcceptor {

    void bind(int port);

    void shutdownGracefully();
}
