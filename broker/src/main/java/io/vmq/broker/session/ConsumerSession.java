package io.vmq.broker.session;

import io.vmq.broker.topic.TopicQueue;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * After handshake: blocks on the topic queue and writes each message with {@link DataOutputStream#writeUTF(String)}.
 */
public final class ConsumerSession {

    private final TopicQueue queue;
    private final DataOutputStream out;

    public ConsumerSession(TopicQueue queue, DataOutputStream out) {
        this.queue = queue;
        this.out = out;
    }

    public void run() throws IOException, InterruptedException {
        while (!Thread.currentThread().isInterrupted()) {
            String message = queue.consume();
            out.writeUTF(message);
            out.flush();
        }
    }
}
