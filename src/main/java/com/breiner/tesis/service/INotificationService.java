package com.breiner.tesis.service;

public interface INotificationService {

    void subscribeUser(String userEmail);
    void subscribeAdmin(String userEmail);
    void publishMessageNewPet(String message, String subject);
    void publishMessageNewAdoptionRequest(String message, String subject);

    void sendEmail(String body, String subject, String emailDestionation);

}
