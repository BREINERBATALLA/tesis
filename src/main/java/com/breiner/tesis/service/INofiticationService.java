package com.breiner.tesis.service;

public interface INofiticationService {

    void subscribeUser(String userEmail);

    void publishMessage(String message, String subject);
}
