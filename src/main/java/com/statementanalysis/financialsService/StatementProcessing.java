package com.statementanalysis.financialsService;

import java.util.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.NoArgsConstructor;

@Configuration
@NoArgsConstructor
@Service
public class StatementProcessing {
    
    void traverseJsonForMap(JsonNode node, Map<String, String> genericMap, String fieldName) {
        if(node.isObject()){
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while(fields.hasNext()){
                Map.Entry<String, JsonNode> entry = fields.next();
                String key = entry.getKey();
                JsonNode value = entry.getValue();
                if(isYear(key) && value.isObject()){
                    String val = value.path(fieldName).asText("Item not found");
                    if(val != "Item not found"){
                        genericMap.put(key.substring(0, 4), val);
                    }
                }
                // Recursively search in the value node
                traverseJsonForMap(value, genericMap, fieldName);
            }
        } else if (node.isArray()) {
            // If the node is an array, iterate over its elements
            node.forEach(element -> traverseJsonForMap(element, genericMap, fieldName));
        }
    }

    String traverseJsonForSingleString(JsonNode node, String fieldName, String finStat){
        JsonNode firstParent = node.get(finStat);
        String finData = firstParent.get(fieldName).asText();
        return finData;
    }

    private boolean isYear(String key) {
        // Basic check if the key represents a year (e.g., "2023-12-31")
        return key.matches("\\d{4}-\\d{2}-\\d{2}");
    }
}
