package core.base.common;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-09
 * Time: 10:19
 */

public interface ISessionTag {
    String getKey();
    void initTag(Object [] params);
}
