package ru.kafka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static ru.kafka.Application.*;


@RequiredArgsConstructor
@RestController
public class FilesController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/files")
    public ResponseEntity<?> sendFile(@RequestParam("fileName") String fileName){
        ListenableFutureCallback<SendResult<String, String>> callback = new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                throw new IllegalArgumentException(ex);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                Objects.requireNonNull(result);
                System.out.println(result.getProducerRecord().value() + " " + result.getRecordMetadata().toString());
            }
        };

        ListenableFuture<SendResult<String, String>> fileSendResult = kafkaTemplate.send(FILES_TOPIC, fileName);

        fileSendResult.addCallback(callback);

        if(fileName.endsWith(".jpg") ||fileName.endsWith(".jpeg") || fileName.endsWith(".png")){
            ListenableFuture<SendResult<String, String>> imagesSendResult = kafkaTemplate.send(IMAGES_TOPIC, fileName);
            imagesSendResult.addCallback(callback);
        } else {
            ListenableFuture<SendResult<String, String>> documentsSendResult = kafkaTemplate.send(DOCUMENTS_TOPIC, fileName);
            documentsSendResult.addCallback(callback);
        }

        return ResponseEntity.ok().build();
    }
}
