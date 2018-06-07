package core.network;

import core.base.concurrent.command.AbstractHandler;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-22
 * Time: 12:13
 */
public interface IResponseHandlerManager {

    HashMap<Integer, Class<? extends AbstractHandler>> handlers = new HashMap<>(10);
    /**
     * 获取handler
     * @param messageId
     * @return
     */
    default AbstractHandler getHandler(int messageId) {
        Class<? extends AbstractHandler> clazz = handlers.get(messageId);
        if (clazz != null) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 注册
     * @param messageId
     * @param handler
     */
    default AbstractHandler register(int messageId, Class<? extends AbstractHandler> handler)
    {
        Class<? extends AbstractHandler> clazz = handlers.get(messageId);
        if (clazz != null) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    void register();
}
