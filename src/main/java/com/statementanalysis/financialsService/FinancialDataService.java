package com.statementanalysis.financialsService;
import java.util.*;
import com.fasterxml.jackson.databind.JsonNode;

public interface FinancialDataService {
    Map<String, String> getNetIncomeData(JsonNode response);
    Map<String, String> getTotalRevenueData(JsonNode response);
    Map<String, String> getEbitdaData(JsonNode response);
    Map<String, String> getInterestExpenseData(JsonNode response);
    Map<String, String> getCFOData(JsonNode response);
    Map<String, String> getFreeCashFlowData(JsonNode response);
    Map<String, String> getCapexData(JsonNode response);
    Map<String, String> getEbitData(JsonNode response);
    Map<String, String> getAssetData(JsonNode response);
    Map<String, String> getLiabilityData(JsonNode response);
    Map<String, Map<String, String>> unixToDateHistory (Map<String, Map<Long, Double>> historymap);
}
