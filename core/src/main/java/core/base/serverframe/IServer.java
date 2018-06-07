package core.base.serverframe;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 11:24
 */

public interface IServer {

    /**
     * start
     */
    void start();

    /**
     * stop
     */
    void stop();

    /**
     * update
     */
    void update();

    /**
     * 获取当前服务的状态
     *
     * @return ServiceState
     */
    boolean getState();

    /**
     * 是否已开启
     *
     * @return boolean
     */
    default boolean isOpened() {
        return getState();
    }

    /**
     * 是否已关闭
     *
     * @return boolean
     */
    default boolean isClosed() {
        return getState();
    }

}
