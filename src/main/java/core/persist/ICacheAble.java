package core.persist;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-23
 * Time: 14:38
 */
public interface ICacheAble {

    /**
     * 获取id
     *
     * @return long
     */
    long getId();

    /**
     * 数据类型
     * @return int
     */
    int dataType();

    /**
     * 获取服务器id
     * @return int
     */
    int getServerId();
}
