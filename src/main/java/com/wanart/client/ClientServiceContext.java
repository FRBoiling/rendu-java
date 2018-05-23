package com.wanart.client;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-24
 * Time: 10:24
 */
public class ClientServiceContext {
    private static ClientOption option;
    private static Client client;
    private static boolean closed;

    public static void init(ClientOption option) {
        ClientServiceContext.option = option;
    }

    public static Client createClient() {
        try {
            client = new Client(option);
            return client;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

}
