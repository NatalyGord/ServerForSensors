package ru.tusur.udo.server.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.tusur.udo.server.dto.AggregationInfo;
import ru.tusur.udo.server.dto.NodeInfo;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

@Service
public class AggregationService {
    private static final Logger LOG = LoggerFactory.getLogger(AggregationService.class);

    private long timestamp;
    private final AggregationInfo aggregationInfo = new AggregationInfo();


    public void aggregate(NodeInfo nodeInfo) {
        timestamp = new Date().getTime();
        aggregationInfo.setAggregationTimestamp(timestamp);
        aggregationInfo.getNodesHashMap().put(nodeInfo.getNodeName(), nodeInfo);

        aggregationInfo.setNodesHashMap(aggregationInfo.getNodesHashMap()
                .entrySet().stream()
                .filter(x -> abs(x.getValue().getTimeStamp() - timestamp) < 5000)
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()))
        );
    }

    public AggregationInfo getData() {
        return aggregationInfo;
    }

}
