package io.vmq.broker.server;

import io.vmq.broker.topic.TopicManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BrokerServer {

    private final int port;

    private final TopicManager topicManager =
            new TopicManager();

    public BrokerServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {

        ServerSocket serverSocket =
                new ServerSocket(port);

        System.out.println("VMQ Broker started on port " + port);

        while (true) {

            Socket socket = serverSocket.accept();

            Thread.startVirtualThread(() -> {
                try {

                    BrokerConnectionHandler handler =
                            new BrokerConnectionHandler(
                                    socket,
                                    topicManager
                            );

                    handler.handle();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}