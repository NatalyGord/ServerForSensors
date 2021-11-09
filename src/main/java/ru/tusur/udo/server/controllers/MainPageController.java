package ru.tusur.udo.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.udo.server.services.AggregationService;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@EnableScheduling
public class MainPageController {

    private static final Logger LOG = LoggerFactory.getLogger(MainPageController.class);

    @Value("${:classpath:/public/index.html}")
    private Resource index;

    @Autowired
    AggregationService aggregationService;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;


    @GetMapping
    public ResponseEntity index() throws IOException {
        return ResponseEntity.ok(new InputStreamResource(index.getInputStream()));
    }

    @Scheduled(fixedDelayString = "${sensors.runtime.interval}")
    public void sendSensorsToSocket() {
        simpMessagingTemplate.convertAndSend("/", aggregationService.getData());
    }


    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map info() {
        return Collections.singletonMap("version", "1.0");
    }




}
