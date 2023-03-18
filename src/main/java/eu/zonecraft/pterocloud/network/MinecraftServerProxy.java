package eu.zonecraft.pterocloud.network;

import eu.zonecraft.pterocloud.network.handler.MinecraftServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MinecraftServerProxy {

    public final Map<String, String> serverAddresses;

    public MinecraftServerProxy() {
        serverAddresses = new HashMap<>();
        // Hier können Sie die Adressen der Minecraft-Server hinzufügen, die Sie unterstützen möchten.
        serverAddresses.put("server1", "127.0.0.1:25565");
        serverAddresses.put("server2", "127.0.0.1:25566");
    }

    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        System.out.println(serverAddresses);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new MinecraftServerHandler(serverAddresses));
                        }
                    });

            ChannelFuture future = bootstrap.bind(25567).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MinecraftServerProxy server = new MinecraftServerProxy();
        server.start();
    }

}
