package network.common;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-04
 * Time: 15:05
 */

public final class NativeSupport {
    private static final boolean SUPPORT_NATIVE_ET;

    static {
        boolean epoll;
        try {
            Class.forName("io.netty.channel.epoll.Native");
            epoll = true;
        } catch (Throwable e) {
            epoll = false;
        }
        SUPPORT_NATIVE_ET = epoll;
    }

    /**
     * The native socket transport for Linux using JNI.
     */
    public static boolean isSupportNativeET() {
        return SUPPORT_NATIVE_ET;
    }
}
