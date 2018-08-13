package core.base.model;

import core.base.common.ISessionTag;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-09
 * Time: 10:44
 */
@Getter
@Setter
@Accessors(chain=true)
public class ClientTag implements ISessionTag {
    private String username;
    private String channelName;

    public ClientTag() {
        username = "";
        channelName ="";
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.username,this.channelName);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClientTag) {
            ClientTag t = (ClientTag) obj;
            return t.username.equals( this.username) &&
                    t.channelName.equals(this.channelName);
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        String strTag = username+ "_" + channelName;
        return strTag;
    }
    //    Object [] tagParam = new Object[]{
//            id,
//            name,
//    };
    @Override
    public void initTag(Object[] params) {
        this.username = (String) params[0];
        this.channelName = (String) params[1];
    }

    public void setTag(String accountName,String channelName) {
        this.username = accountName;
        this.channelName = channelName;
    }


}
