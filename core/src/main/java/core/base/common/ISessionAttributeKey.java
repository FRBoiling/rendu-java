package core.base.common;


import io.netty.util.AttributeKey;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-23
 * Time: 14:33
 */
public interface ISessionAttributeKey {
    /**
     * 用户ID
     */
    AttributeKey<Long> UID = AttributeKey.valueOf("UID");

    AttributeKey<String> LOGINNAME = AttributeKey.valueOf("loginName");

    AttributeKey<Integer> SERVERID = AttributeKey.valueOf("serverId");
}
