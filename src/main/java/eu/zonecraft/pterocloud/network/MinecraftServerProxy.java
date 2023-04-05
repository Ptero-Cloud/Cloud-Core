package eu.zonecraft.pterocloud.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class MinecraftServerProxy extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof String) {
            String message = (String) msg;
            if (message.startsWith("Player connected: ")) {
                String playerName = message.substring("Player connected: ".length());
                System.out.println(playerName + " has connected to the server.");
                // Automatically redirect the player to the lobby server
                redirectToLobbyServer(ctx);
            }
        }
        ctx.fireChannelRead(msg);
    }

    private void redirectToLobbyServer(ChannelHandlerContext ctx) {
        // Connect to the lobby server on port 25566
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(ctx.channel().eventLoop())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) {
                                // Send the player's connection data to the lobby server
                                ctx.writeAndFlush("ServerConnector|" + ctx.channel().remoteAddress() + "|Lobby");
                                // Close the connection to the BungeeCord server
                                ctx.close();
                            }
                        });
                    }
                });
        bootstrap.connect(new InetSocketAddress("localhost", 255676));
    }

    public static void main(String[] args) throws Exception {
        // Start the BungeeCord server on port 25565
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast((ChannelHandler) new MinecraftServerHandler("localhost", 25567));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(25564).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}