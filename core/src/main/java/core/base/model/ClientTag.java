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
    private int token;

    private String strTag;

    public ClientTag() {
        accountName = "";
        token =0;
        strTag="";
    }

    @Override
    public int hashCode(){
        return this.accountName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClientTag) {
            ClientTag t = (ClientTag) obj;
            return t.accountName.equals( this.accountName) &&
                    t.token == this.token;
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
        this.token = (int) params[1];

        this.strTag = this.strTag+ "_" + this.accountName;
        this.strTag = this.strTag+ "_" + this.token;
    }

    public void setTag(String accountName,int token) {
        this.accountName = accountName;
        this.token = token;

        this.strTag = this.strTag+ "_" + this.accountName;
        this.strTag = this.strTag+ "_" + this.token;
    }


}
