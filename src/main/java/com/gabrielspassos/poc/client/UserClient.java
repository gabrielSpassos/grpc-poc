package com.gabrielspassos.poc.client;

import com.gabrielspassos.poc.proto.user.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class UserClient {

    public static void main(String[] args) {
        UserClient userClient = new UserClient();
        userClient.run();
    }

    private void run() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        UserServiceGrpc.UserServiceBlockingStub userClient = UserServiceGrpc.newBlockingStub(channel);

        // Create
        User user = User.newBuilder()
                .setName("luiz")
                .setEmail("teste@teste.com")
                .build();
        CreateUserResponse createUserResponse = userClient.createUser(
                CreateUserRequest.newBuilder()
                        .setUser(user)
                        .build()
        );
        System.out.println(createUserResponse.toString());

        // Delete
        String userId = createUserResponse.getUser().getId();
        DeleteUserResponse deleteUserResponse = userClient.deleteUser(
                DeleteUserRequest.newBuilder().setUserId(userId).build()
        );
        System.out.println(deleteUserResponse.toString());

        // List
        userClient.listUser(ListUserRequest.newBuilder().build()).forEachRemaining(
                listUserResponse -> System.out.println(listUserResponse.getUser().toString())
        );
    }
}
