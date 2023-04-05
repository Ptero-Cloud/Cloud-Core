package eu.zonecraft.pterocloud.network.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Arrays;
import java.util.Map;

public class MinecraftServerHandler extends ChannelInboundHandlerAdapter {

    private final Map<String, String> serverAddresses;
    private Channel remoteChannel;

    public MinecraftServerHandler(Map<String, String> serverAddresses) {
        this.serverAddresses = serverAddresses;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Hier können Sie die Anfragen von Spielern verarbeiten und ihnen den Zugriff auf die verschiedenen Server ermöglichen.
        String serverName = (String) msg;
        System.out.println(serverName);
        if (serverAddresses.containsKey(serverName)) {
            if (remoteChannel == null) {
                connectToServer(ctx, serverName);
            }

            // Hier können Sie den Spieler an den Remote-Server weiterleiten.
            remoteChannel.writeAndFlush(serverAddresses.get(serverName) + "\r\n");
        } else {
            ctx.writeAndFlush("Unknown server: " + serverName + "\r\n");
        }
    }

    private void connectToServer(ChannelHandlerContext ctx, String serverName) throws InterruptedException {
        String[] address = serverAddresses.get(serverName).split(":");
        System.out.println(Arrays.toString(address));
        String host = address[0];
        int port = Integer.parseInt(address[1]);

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(ctx.channel().eventLoop())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ForwardHandler(ctx.channel()));
                    }
                });

        ChannelFuture future = bootstrap.connect(host, port).sync();
        remoteChannel = future.channel();
    }
}
