package example;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import network.client.connector.DefaultCommonClientConnector;
import network.codec.Packet;
import protocol.gate.global.G2GM;
import protocol.gate.global.G2GMIdGenerater;
import protocol.global.gate.GM2GIdGenerater;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-04
 * Time: 16:50
 */
@Slf4j
public class ClientConnectorStartup {
    public static void main(String[] args) {
        DefaultCommonClientConnector clientConnector = new DefaultCommonClientConnector();
        Channel channel = clientConnector.connect(20011, "127.0.0.1");


        G2GMIdGenerater.GenerateId();
        GM2GIdGenerater.GenerateId();
        log.info("ClientConnectorStartup ready ...");

        G2GM.MSG_G2GM_REQ_Register.Builder builder = G2GM.MSG_G2GM_REQ_Register.newBuilder();
        builder.setId(1);
        //获取到channel发送双方规定的message格式的信息
        channel.writeAndFlush(builder.build()).addListener(new ChannelFutureListener() {

            public void operationComplete(ChannelFuture future) throws Exception {
                if(!future.isSuccess()) {
                    log.info("send fail,reason is {}",future.cause().getMessage());
                }
            }
        });
//        channel.writeAndFlush(builder.build());

//        //防止对象处理发生异常的情况
//        MessageNonAck msgNonAck = new MessageNonAck(message, channel);
//        clientConnector.addNeedAckMessageInfo(msgNonAck);
    }
}
