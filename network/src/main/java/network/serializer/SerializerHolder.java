package network.serializer;

import network.serializer.protostuff.ProtoStuffSerializer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-02
 * Time: 16:59
 */

public final class SerializerHolder {
    //使用google的protostuff
    //protostuff 是一个支持各种格式的一个序列化Java类库，包括 JSON、XML、YAML等格式。
    private static final ISerializer serializer = new ProtoStuffSerializer();

    public static ISerializer serializerImpl() {
        return serializer;
    }
}
