package core.base.model;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-09
 * Time: 14:39
 */

public class AbstractServiceContext {
    public static ServerTag tag;

    public static void Init()
    {
        tag = new ServerTag();
        ServerType serverType =ServerType.Gate;
        int groupId =2;
        int subId =1;
        Object [] tagParam = new Object[]{
                serverType,
                groupId,
                subId
        };
        tag.initTag(tagParam);
    }

}
