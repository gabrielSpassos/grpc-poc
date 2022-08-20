package com.gabrielspassos.poc.server;

import com.gabrielspassos.poc.service.UserService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class UserServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Start server");
        Server server = ServerBuilder.forPort(50051)
                .addService(new UserService())
                .build();
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
        server.awaitTermination();
    }
}
