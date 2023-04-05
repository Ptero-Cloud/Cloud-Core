package eu.zonecraft.pterocloud.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

public class MinecraftServerHandler {

    private final String host;
    private final int port;

    public MinecraftServerHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new BungeeCordInitializer());

            ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port)).sync();
            System.out.println("Connected to BungeeCord server");

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    private static class BungeeCordInitializer extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel channel) {
            channel.pipeline()
                    .addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            if (msg instanceof byte[]) {
                                byte[] data = (byte[]) msg;
                                if (data.length > 1 && data[0] == 0x02) {
                                    InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                                    int port = 25566; // set the redirect port
                                    ctx.channel().writeAndFlush(new byte[]{(byte) 0x01, (byte) (port >> 8), (byte) port});

                                    System.out.println("Redirecting player from " + address.getHostString() + ":" + address.getPort() + " to " + address.getHostString() + ":" + port);
                                    return;
                                }
                            }
                            super.channelRead(ctx, msg);
                        }
                    });
        }
    }
}