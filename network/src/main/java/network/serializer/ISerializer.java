package network.serializer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-02
 * Time: 16:58
 */

public interface ISerializer {
    /**
     * 将obj序列化成byte数组
     * @param obj
     * @return
     */
    <T> byte[] writeObject(T obj);

    /**
     * 将byte数组反序列化成class是clazz的obj对象
     * @param bytes
     * @param clazz
     * @return
     */
    <T> T readObject(byte[] bytes, Class<T> clazz);
}
