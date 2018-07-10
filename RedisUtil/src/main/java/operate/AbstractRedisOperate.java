package operate;

import basicCallBack.ObjectBeCalled;
import basicCallBack.ObjectWithCallBack;
import basic.*;
import redis.clients.jedis.*;

public abstract class AbstractRedisOperate extends ObjectWithCallBack {

    protected Object m_result;

    public String ErrorText;

    protected RedisManager manager=null;
    public Jedis jedis=null;

    public void PostUpdate(){
        if (!callbacks.isEmpty()){
            for (ObjectBeCalled call:callbacks) {
                call.call(this,arg);
            }
        }
    }

    abstract public boolean execute();

    public void Init(){
        manager=RedisManager.getInstance();
    }

    public String ErrorLogText(Exception e){
        String logText="";
        if(manager==null){
            logText="no manager";
        }

        return String.format("%s:%s",logText,e.getMessage());
    }

    public String makeKey(String key,Object... suffix){
        return String.format(key,suffix);
    }
}
