package operate.character;

import keys.HashKeyConst;
import operate.AbstractRedisOperate;

public class CharacterOperate extends AbstractRedisOperate {

    int pcUid;

    public CharacterOperate(int pcUid){

    }

    public boolean execute(){
        try{
            Init();
            int dbNum=manager.jedisConfig.getDbNum();
            jedis=manager.getCluster();

            String key=makeKey(HashKeyConst.CharacterInfo,pcUid);
            jedis.hset(key,"name","李四");

            System.out.println("test from redis CharacterOperate");
           // String value=jedis.get("name");
            //System.out.println(value);
        }catch (Exception e){
            m_result=-1;
            ErrorText=ErrorLogText(e);
            return false;
        }

        return true;
    }
}
