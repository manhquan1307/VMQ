package io.vmq.broker.session;

import io.vmq.broker.topic.TopicQueue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public final class ProducerSession {

    private final TopicQueue queue;
    private final BufferedReader in;
    private final BufferedWriter out;

    public ProducerSession(TopicQueue queue, BufferedReader in, BufferedWriter out) {
        this.queue = queue;
        this.in = in;
        this.out = out;
    }

    public void run() throws IOException, InterruptedException {
        String line;
        while ((line = in.readLine()) != null) {
            queue.publish(line);
            out.write("OK");
            out.newLine();
            out.flush();
        }
    }
}
