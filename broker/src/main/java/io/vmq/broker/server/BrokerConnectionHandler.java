package io.vmq.broker.server;

import io.vmq.broker.topic.TopicManager;
import io.vmq.broker.topic.TopicQueue;

import java.io.*;
import java.net.Socket;

public class BrokerConnectionHandler {

    private final Socket socket;

    private final TopicManager topicManager;

    public BrokerConnectionHandler(
            Socket socket,
            TopicManager topicManager
    ) {
        this.socket = socket;
        this.topicManager = topicManager;
    }

    public void handle() throws Exception {

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()
                        )
                );

        BufferedWriter writer =
                new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream()
                        )
                );

        String line;

        while ((line = reader.readLine()) != null) {

            String[] parts = line.split(" ", 3);

            String command = parts[0];

            if ("PUBLISH".equals(command)) {

                String topic = parts[1];
                String message = parts[2];

                TopicQueue topicQueue =
                        topicManager.getOrCreateTopic(topic);

                topicQueue.publish(message);

                writer.write("OK\n");
                writer.flush();
            }
        }
    }
}