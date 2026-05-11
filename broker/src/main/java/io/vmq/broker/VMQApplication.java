package io.vmq.broker;

import io.vmq.broker.server.BrokerServer;

public class VMQApplication {

    public static void main(String[] args) throws Exception {

        BrokerServer brokerServer = new BrokerServer(9092);

        brokerServer.start();
    }
}