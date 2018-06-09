package core.base.model;

import core.base.common.ISessionTag;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-09
 * Time: 10:44
 */
@Getter
public class ServerTag implements ISessionTag {
    private int groupId;
    private int subId;
    private ServerType type;
    private String strTag;

    @Override
    public String getKey() {
        return strTag;
    }

    @Override
    public void initTag(Object[] params) {
        this.type =(ServerType)params[0];
        this.groupId =(int)params[1];
        this.subId =(int)params[2];
        strTag = type.name()+"_"+groupId+"_"+subId;
    }
}
