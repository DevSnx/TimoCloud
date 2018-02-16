package cloud.timo.TimoCloud.cord.sockets;

import cloud.timo.TimoCloud.cord.TimoCloudCord;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

import static cloud.timo.TimoCloud.cord.utils.PacketUtil.releaseByteBuf;

public class ProxyDownstreamHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private Channel channel;

    public ProxyDownstreamHandler(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
        ByteBuf clone = Unpooled.copiedBuffer(buf);
        getChannel().writeAndFlush(clone);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //if (getChannel().isActive()) MinecraftDecoder.connectClient(channel, true);
        getChannel().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        TimoCloudCord.getInstance().severe("Exception in DownStreamHandler");
        cause.printStackTrace();
    }

    public Channel getChannel() {
        return channel;
    }
}
