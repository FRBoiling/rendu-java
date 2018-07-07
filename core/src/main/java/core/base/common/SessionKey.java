package core.base.common;

import io.netty.util.AttributeKey;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-04-23
 * Time: 14:52
 */
public class SessionKey {
    public static final AttributeKey<AbstractSession> SESSION = AttributeKey.newInstance("SESSION");
    public static final AttributeKey<Boolean> LOGOUT_HANDLED = AttributeKey.newInstance("LOGOUT_HANDLED");
}
