package com.statementanalysis.utils;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
@Component
public class Utils {
    public void extractHistoryData(JsonNode node, Map<Long, Double> map){
        node.fields().forEachRemaining(entry -> {
            map.put(Long.parseLong(entry.getKey()), entry.getValue().asDouble());
        });
    }
}
