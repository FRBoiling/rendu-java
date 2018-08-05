package core.network;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-08
 * Time: 9:46
 */

public class NativeSupport {
    private static final boolean SUPPORT_NATIVE_ET;

    static {
        boolean hasEpoll;
        try {
            Class.forName("io.netty.channel.epoll.Native");
            hasEpoll = true;
        } catch (Throwable e) {
            hasEpoll = false;
        }
        SUPPORT_NATIVE_ET = hasEpoll;
    }

    /**
     * The native socket transport for Linux using JNI.
     */
    public static boolean isSupportNativeET() {
        return SUPPORT_NATIVE_ET;
    }
}
