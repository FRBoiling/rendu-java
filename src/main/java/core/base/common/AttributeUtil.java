package core.base.common;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-23
 * Time: 14:29
 */
public class AttributeUtil {
    public static <T> T get(Channel channel, AttributeKey<T> key){
        return channel.attr(key).get();
    }

    public static <T> void set(Channel channel, AttributeKey<T> key, T value){
        channel.attr(key).set(value);
    }
}
