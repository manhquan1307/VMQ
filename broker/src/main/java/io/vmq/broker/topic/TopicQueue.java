package io.vmq.broker.topic;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TopicQueue {

    private final String topicName;

    private final BlockingQueue<String> messages =
            new LinkedBlockingQueue<>();

    public TopicQueue(String topicName) {
        this.topicName = topicName;
    }

    public void publish(String message) {
        messages.offer(message);
    }

    public String consume() throws InterruptedException {
        return messages.take();
    }

    public String getTopicName() {
        return topicName;
    }
}