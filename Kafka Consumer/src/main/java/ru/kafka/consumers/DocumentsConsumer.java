package ru.kafka.consumers;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Profile("documents-consumer")
@Component
public class DocumentsConsumer {

    @KafkaListener(topics = "documents", groupId = "documentsGroupId")
    public void listenDocuments(@Payload String message) {
        System.out.println("Documents " + message);
    }
}

