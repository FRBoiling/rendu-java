package example;

import lombok.extern.slf4j.Slf4j;
import network.server.acceptor.DefaultCommonServerAcceptor;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-04
 * Time: 16:50
 */
@Slf4j
public class ServerAcceptorStartup {
    public static void main(String[] args) throws InterruptedException {
        DefaultCommonServerAcceptor defaultCommonSrvAcceptor = new DefaultCommonServerAcceptor(20011,null);
        defaultCommonSrvAcceptor.start();
        log.info("ServerAcceptorStartup ready ...");
    }
}
