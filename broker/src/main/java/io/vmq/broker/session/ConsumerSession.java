package io.vmq.broker.session;

import io.vmq.broker.topic.TopicQueue;

import java.io.BufferedWriter;
import java.io.IOException;

public final class ConsumerSession {

    private final TopicQueue queue;
    private final BufferedWriter out;

    public ConsumerSession(TopicQueue queue, BufferedWriter out) {
        this.queue = queue;
        this.out = out;
    }

    public void run() throws IOException, InterruptedException {
        while (!Thread.currentThread().isInterrupted()) {
            String message = queue.consume();
            out.write(message);
            out.newLine();
            out.flush();
        }
    }
}
