package core.network;

import java.util.concurrent.TimeUnit;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boil
 * Date: 2018-08-19
 * Time: 14:28
 **/
public class NetWorkConst {
    public static final int WRITER_IDLE_TIME =60;// 5*60; //userEventTriggered 写间隔               //new IdleStateHandler(0, NetWorkConst.WRITER_IDLE_TIME,0,TimeUnit.SECONDS), 单位秒
    public static final int READER_IDLE_TIME =65;// 5*61; //userEventTriggered 读间隔               //new IdleStateHandler(NetWorkConst.READER_IDLE_TIME, 0, 0,TimeUnit.SECONDS),单位秒
}
