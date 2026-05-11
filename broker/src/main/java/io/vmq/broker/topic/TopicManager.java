package io.vmq.broker.topic;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class TopicManager {

    private final ConcurrentHashMap<String, TopicQueue> topics = new ConcurrentHashMap<>();

    public TopicQueue getOrCreateTopic(String topicName) {
        Objects.requireNonNull(topicName, "topicName");
        String name = topicName.trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("topic name must not be blank");
        }
        return topics.computeIfAbsent(name, TopicQueue::new);
    }
}
