package network.serializer.protostuff;

import network.serializer.ISerializer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-02
 * Time: 16:58
 */

public class ProtoStuffSerializer implements ISerializer {

//    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();

//    private static Objenesis objenesis = new ObjenesisStd(true);

    @SuppressWarnings("unchecked")
    public <T> byte[] writeObject(T obj) {

//        Class<T> cls = (Class<T>) obj.getClass();
//        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
//        try {
//            Schema<T> schema = getSchema(cls);
//            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
//        } catch (Exception e) {
//            throw new IllegalStateException(e.getMessage(), e);
//        } finally {
//            buffer.clear();
//        }
        return  null;
    }

    public <T> T readObject(byte[] bytes, Class<T> clazz) {
//        try {
//            T message = objenesis.newInstance(clazz);
//            Schema<T> schema = getSchema(clazz);
//            ProtostuffIOUtil.mergeFrom(bytes, message, schema);
//            return message;
//        } catch (Exception e) {
//            throw new IllegalStateException(e.getMessage(), e);
//        }
        return  null;
    }

//    @SuppressWarnings("unchecked")
//    private static <T> Schema<T> getSchema(Class<T> cls) {
//        Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
//        if (schema == null) {
//            schema = RuntimeSchema.createFrom(cls);
//            cachedSchema.put(cls, schema);
//        }
//        return schema;
//    }
}

