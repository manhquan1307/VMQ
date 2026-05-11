package io.vmq.broker.session;

import io.vmq.broker.topic.TopicQueue;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * After handshake: each {@link DataInputStream#readUTF()} from the client is published to the topic.
 */
public final class ProducerSession {

    private static final Logger LOG = Logger.getLogger(ProducerSession.class.getName());

    private final TopicQueue queue;
    private final DataInputStream in;

    public ProducerSession(TopicQueue queue, DataInputStream in) {
        this.queue = queue;
        this.in = in;
    }

    public void run() throws IOException, InterruptedException {
        while (true) {
            String message;
            try {
                message = in.readUTF();
            } catch (EOFException e) {
                LOG.fine("producer closed socket");
                return;
            }
            queue.publish(message);
        }
    }
}
