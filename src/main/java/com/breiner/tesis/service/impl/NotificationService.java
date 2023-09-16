package com.breiner.tesis.service.impl;

import com.breiner.tesis.service.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;

@RequiredArgsConstructor
@Service
public class NotificationService implements INotificationService {

    private final SnsClient snsClient;
    private final SesClient sesClient;
    @Value("${aws.topic-arn-new-pet}")
    private String topicArnNewPet;

    @Value("${aws.topic-arn-adoption-request}")
    private String topicArnNewAdoptionrequest;

    @Value("${aws.email}")
    private String email;

    public void subscribeUser(String userEmail) {
        SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .topicArn(topicArnNewPet)
                .protocol("email")
                .endpoint(userEmail)
                .build();
        snsClient.subscribe(subscribeRequest);
        ///topic sent request adoption -- 1 para todos(users, admins) (al crear la solicitud) para enviar msjs cuando se actualice el estado.
                            //toca hacer métodos para ambos topicos
                            // topic al registrar una mascota a los users(adoptant) al crear el usuario y envio msj al crear una mascota.
                            //   snsClient.subscribe(subscribeRequest);
    }

    public void subscribeAdmin(String userEmail) {
        SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .topicArn(topicArnNewAdoptionrequest)
                .protocol("email")
                .endpoint(userEmail)
                .build();
        snsClient.subscribe(subscribeRequest);
        ///topic sent request adoption -- 1 para todos(users, admins) (al crear la solicitud) para enviar msjs cuando se actualice el estado.
        //toca hacer métodos para ambos topicos
        // topic al registrar una mascota a los users(adoptant) al crear el usuario y envio msj al crear una mascota.
        //   snsClient.subscribe(subscribeRequest);
    }
    //ver SS clase para enviar los mensajes...
    public void publishMessageNewPet(String message, String subject) {
        PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(topicArnNewPet)
                .message(message)
                .subject(subject)
                .build();
       snsClient.publish(publishRequest);
    }

    public void publishMessageNewAdoptionRequest(String message, String subject) {
        PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(topicArnNewAdoptionrequest)
                .message(message)
                .subject(subject)
                .build();
        snsClient.publish(publishRequest);
    }

    @Override
    public void sendEmail(String body, String subject, String emailDestionation) {
        Destination destination = Destination.builder()
                .toAddresses(emailDestionation)
                .build();

        Body bodyEmail = Body.builder()
                .text(Content.builder().data(body).build())
                .build();

        Message message = Message.builder()
                .subject(Content.builder().data(subject).build())
                .body(bodyEmail)
                .build();

        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .source(email)
                .message(message)
                .destination(destination)
                .build();

        sesClient.sendEmail(emailRequest);
    }
}