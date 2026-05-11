package io.vmq.broker.server;

import io.vmq.broker.session.ConsumerSession;
import io.vmq.broker.session.ProducerSession;
import io.vmq.broker.topic.TopicManager;
import io.vmq.broker.topic.TopicQueue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class BrokerConnectionHandler {

    private final Socket socket;
    private final TopicManager topicManager;

    public BrokerConnectionHandler(Socket socket, TopicManager topicManager) {
        this.socket = socket;
        this.topicManager = topicManager;
    }

    public void handle() throws Exception {
        try (Socket s = socket) {
            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter writer =
                    new BufferedWriter(
                            new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8));

            String first = reader.readLine();
            if (first == null) {
                return;
            }

            String trimmed = first.trim();
            String[] head = trimmed.split("\\s+", 2);
            if (head.length == 0) {
                return;
            }

            String role = head[0];

            if ("PUBLISH".equalsIgnoreCase(role)) {
                String[] publishParts = trimmed.split("\\s+", 3);
                if (publishParts.length < 3) {
                    writer.write("ERR PUBLISH needs topic and message");
                    writer.newLine();
                    writer.flush();
                    return;
                }
                String pubTopic = publishParts[1];
                String msg = publishParts[2];
                topicManager.getOrCreateTopic(pubTopic).publish(msg);
                writer.write("OK");
                writer.newLine();
                writer.flush();
                return;
            }

            if (head.length < 2) {
                writer.write("ERR expected PRODUCER <topic> or CONSUMER <topic>");
                writer.newLine();
                writer.flush();
                return;
            }

            String topic = head[1].trim();
            if (topic.isEmpty()) {
                writer.write("ERR topic name is blank");
                writer.newLine();
                writer.flush();
                return;
            }

            TopicQueue topicQueue = topicManager.getOrCreateTopic(topic);

            if ("PRODUCER".equalsIgnoreCase(role)) {
                writer.write("OK");
                writer.newLine();
                writer.flush();
                new ProducerSession(topicQueue, reader, writer).run();
            } else if ("CONSUMER".equalsIgnoreCase(role)) {
                writer.write("OK");
                writer.newLine();
                writer.flush();
                new ConsumerSession(topicQueue, writer).run();
            } else {
                writer.write("ERR unknown command: use PRODUCER, CONSUMER, or PUBLISH");
                writer.newLine();
                writer.flush();
            }
        }
    }
}
