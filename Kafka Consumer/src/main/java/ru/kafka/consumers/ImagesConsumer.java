package ru.kafka.consumers;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Profile("images-consumer")
@Component
public class ImagesConsumer {
    @KafkaListener(topics="images", groupId = "imagesGroupId")
    public void listenImages(@Payload String message){
        System.out.println("Images " + message);
    }
}
