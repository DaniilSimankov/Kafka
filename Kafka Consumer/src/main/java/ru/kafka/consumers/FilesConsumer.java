package ru.kafka.consumers;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Profile("files-consumer")
@Component
public class FilesConsumer {
    @KafkaListener(topics = "files", groupId = "filesGroupId")
    public void listenFiles(@Payload String message){
        System.out.println("Files " + message);
    }

}
