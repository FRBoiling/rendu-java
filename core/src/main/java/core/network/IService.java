package core.network;


public interface IService {
    /**
     * start
     */
    void start();

    /**
     * stop
     */
    void stop();

    /**
     * 获取当前服务的状态
     *
     * @return ServiceState
     */
    ServiceState getState();

    /**
     * 是否已开启
     *
     * @return boolean
     */
    default boolean isOpened() {
        return getState() == ServiceState.RUNNING;
    }

    /**
     * 是否已关闭
     *
     * @return boolean
     */
    default boolean isClosed() {
        return getState() == ServiceState.STOPPED;
    }
}
