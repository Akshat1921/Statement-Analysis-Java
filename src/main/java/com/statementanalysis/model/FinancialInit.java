package com.statementanalysis.model;
import java.util.Map;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@Data
public class FinancialInit {
    private Map<String, Map<String, String>> finIndicator;
    private Map<String, Object> additionalData;
    private Map<String, Map<Long, Double>> historyDatMap;
    private Map<String, Map<String, String>> updatedTimeMap;
}
