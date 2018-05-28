package core.network;

import core.base.concurrent.command.AbstractHandler;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-22
 * Time: 12:13
 */
public interface IMessageAndHandler {
//    /**
//     * 获取消息
//     *
//     * @param messageId
//     * @return
//     */
//    AbstractMessage getMessage(int messageId);
//
//    /**
//     * 获取消息id
//     * @param message
//     * @return
//     */
//    int getMessageId(AbstractMessage message);

    /**
     * 获取handler
     * @param messageId
     * @return
     */
    AbstractHandler getHandler(int messageId);

    /**
     * 注册
     * @param messageId
     * @param handler
     */
    void register(int messageId, Class<? extends AbstractHandler> handler);

    void register();
}
