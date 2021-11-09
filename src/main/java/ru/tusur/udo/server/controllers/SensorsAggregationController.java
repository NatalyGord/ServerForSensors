package ru.tusur.udo.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.udo.server.dto.NodeInfo;
import ru.tusur.udo.server.services.AggregationService;

@RestController
public class SensorsAggregationController {

    @Autowired
    AggregationService aggregationService;

    @PostMapping(value = "/aggregate")
    public void aggregate(@RequestBody NodeInfo nodeInfo) {
      aggregationService.aggregate(nodeInfo);
    }

}
