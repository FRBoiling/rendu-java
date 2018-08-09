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

    //    HashMap<Integer, Class<? extends IResponseHandler>> handlers = new HashMap<>(10);
    HashMap<Integer, IResponseHandler> handlers = new HashMap<>(10);

//    /**
//     * 获取handler
//     * @param messageId 消息id
//     * @return 返回处理类
//     */
//    default IResponseHandler getHandler(int messageId) {
//        Class<? extends IResponseHandler> clazz = handlers.get(messageId);
//        if (clazz != null) {
//            try {
//                return clazz.newInstance();
//            } catch (Exception e) {
//                return null;
//            }
//        }
//        return null;
//    }

    /**
     * 获取handler
     *
     * @param messageId 消息id
     * @return 返回处理类
     */
    default IResponseHandler getHandler(int messageId) {
        return handlers.get(messageId);
    }

    /**
     * 注册
     *
     * @param messageId 消息id
     * @param handler   消息响应类
     */
    default boolean registerHandler(int messageId, Class<? extends IResponseHandler> handler) {
        try {
            handlers.put(messageId, handler.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    void registerHandlers();
}
