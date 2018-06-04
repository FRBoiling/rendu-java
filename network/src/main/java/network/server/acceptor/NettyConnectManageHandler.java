package network.server.acceptor;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;
import network.common.NettyEvent;
import network.common.NettyEventType;

import java.net.SocketAddress;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-04
 * Time: 14:33
 */

@Slf4j
public class NettyConnectManageHandler extends ChannelDuplexHandler {
    AbstractAcceptor abstractAcceptor;

    public NettyConnectManageHandler(AbstractAcceptor abstractAcceptor) {
        this.abstractAcceptor = abstractAcceptor;
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise future) throws Exception {
        final String local = localAddress == null ? "UNKNOW" : localAddress.toString();
        final String remote = remoteAddress == null ? "UNKNOW" : remoteAddress.toString();
        log.info("NETTY CLIENT PIPELINE: CONNECT  {} => {}", local, remote);
        super.connect(ctx, remoteAddress, localAddress, future);

        if (abstractAcceptor.getChannelEventListener() != null) {
            abstractAcceptor.putNettyEvent(new NettyEvent(NettyEventType.CONNECT, remoteAddress.toString(), ctx.channel()));
        }
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise future) throws Exception {
        final String remoteAddress = ctx.channel().remoteAddress().toString();
        log.info("NETTY CLIENT PIPELINE: DISCONNECT {}", remoteAddress);
        super.disconnect(ctx, future);

        if (abstractAcceptor.getChannelEventListener() != null) {
            abstractAcceptor.putNettyEvent(new NettyEvent(NettyEventType.CLOSE, remoteAddress.toString(), ctx.channel()));
        }
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        final String remoteAddress = ctx.channel().remoteAddress().toString();
        log.info("NETTY CLIENT PIPELINE: CLOSE {}", remoteAddress);
        super.close(ctx, promise);

        if (abstractAcceptor.getChannelEventListener() != null) {
            abstractAcceptor.putNettyEvent(new NettyEvent(NettyEventType.CLOSE, remoteAddress.toString(), ctx.channel()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        final String remoteAddress = ctx.channel().remoteAddress().toString();
        log.warn("NETTY CLIENT PIPELINE: exceptionCaught {}", remoteAddress);
        log.warn("NETTY CLIENT PIPELINE: exceptionCaught exception.", cause);
        if (abstractAcceptor.getChannelEventListener() != null) {
            abstractAcceptor.putNettyEvent(new NettyEvent(NettyEventType.EXCEPTION, remoteAddress.toString(), ctx.channel()));
        }
    }
}
