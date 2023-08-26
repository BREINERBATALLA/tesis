package com.breiner.tesis.service;

public interface NofiticationService {

    void subscribeUser(String userEmail);

    void publishMessage(String message, String subject);
}
