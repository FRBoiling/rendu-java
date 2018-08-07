package core.base.model;

import core.base.common.ISessionTag;
import lombok.Getter;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-09
 * Time: 10:44
 */
@Getter
public class ClientTag implements ISessionTag {
    private String accountName;
    private String channelName;

    private String strTag;

    public ClientTag() {
        accountName = "";
        channelName ="";
        strTag="";
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.accountName,this.channelName);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClientTag) {
            ClientTag t = (ClientTag) obj;
            return t.accountName.equals( this.accountName) &&
                    t.channelName.equals(this.channelName);
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return strTag;
    }
    //    Object [] tagParam = new Object[]{
//            id,
//            name,
//    };
    @Override
    public void initTag(Object[] params) {
        this.accountName = (String) params[0];
        this.channelName = (String) params[1];

        this.strTag = this.strTag+ "_" + this.accountName;
        this.strTag = this.strTag+ "_" + this.channelName;
    }

    public void setTag(String accountName,String token) {
        this.accountName = accountName;
        this.channelName = token;

        this.strTag = this.strTag+ "_" + this.accountName;
        this.strTag = this.strTag+ "_" + this.channelName;
    }


}
