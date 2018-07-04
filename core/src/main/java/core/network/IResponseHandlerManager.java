package core.network;

import core.base.sequence.IResponseHandler;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-22
 * Time: 12:13
 */
public interface IResponseHandlerManager {

    HashMap<Integer, Class<? extends IResponseHandler>> handlers = new HashMap<>(10);
    /**
     * 获取handler
     * @param messageId
     * @return
     */
    default IResponseHandler getHandler(int messageId) {
        Class<? extends IResponseHandler> clazz = handlers.get(messageId);
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
    default boolean register(int messageId, Class<? extends IResponseHandler> handler)
    {
        Class<? extends IResponseHandler> clazz = handlers.get(messageId);
        if (clazz != null) {
            return false;
        }
        else{
            handlers.put(messageId, handler);
        }
        return true;
    }

    void register();
}
