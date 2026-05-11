package io.vmq.broker.topic;

import java.util.concurrent.ConcurrentHashMap;

public class TopicManager {

    private final ConcurrentHashMap<String, TopicQueue> topics =
            new ConcurrentHashMap<>();

    public TopicQueue getOrCreateTopic(String topicName) {

        return topics.computeIfAbsent(
                topicName,
                TopicQueue::new
        );
    }
}