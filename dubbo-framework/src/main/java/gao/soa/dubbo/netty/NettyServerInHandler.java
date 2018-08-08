package gao.soa.dubbo.netty;

import gao.soa.dubbo.util.InvokeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerInHandler extends ChannelInboundHandlerAdapter {

    @Override
    public boolean isSharable() {
        return super.isSharable();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf result = (ByteBuf)msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        String resultStr = new String(result1);
        System.out.println("客户端发送的数据 : " + resultStr);
        result.release();
        String response = InvokeUtil.invokeService(resultStr);
        
        //这个就是返回值的响应过程
        ByteBuf encode = ctx.alloc().buffer(4 * response.length());
        encode.writeBytes(response.getBytes());
        ctx.writeAndFlush(encode);
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }
}
