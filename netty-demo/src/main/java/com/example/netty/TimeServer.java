//package com.example.netty;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//
///**
// * @author hui
// * @description Netty 时间服务器服务端
// * @date 2021/2/7
// */
//public class TimeServer {
//
//    public static void main(String[] args) throws Exception {
//        int port = 8080;
//        if (args != null && args.length > 0) {
//            try {
//                port = Integer.parseInt(args[0]);
//            } catch (NumberFormatException e) {
//
//            }
//        }
//        new TimeServer().bind(port);
//    }
//
//    public void bind(int port) throws Exception {
//
//        // 配置服务器端的 NIO 线程组
//        EventLoopGroup bossGroup = new NioEventLoopGroup();
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//
//        try {
//            ServerBootstrap b = new ServerBootstrap();
//            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_BACKLOG, 1024)
//                    .childHandler(new ChildChannelHandler());
//
//            // 绑定端口，等待同步成功
//            ChannelFuture f = b.bind(port).sync();
//
//            // 等待服务器监听端口关闭
//            f.channel().closeFuture().sync();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // 优雅退出，释放线程池资源
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
//        }
//
//    }
//
//    private class ChildChannelHandler  extends ChannelInitializer<SocketChannel> {
//
//        @Override
//        protected void initChannel(SocketChannel socketChannel) throws Exception {
//            socketChannel.pipeline().addLast(new TimeServerHandler());
//        }
//    }
//}
//
//
