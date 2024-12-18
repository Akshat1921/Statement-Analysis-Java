package com.statementanalysis.financialsService;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class FinancialDataServiceImpl implements FinancialDataService {
    @Autowired
    private StatementProcessing statementProcessing;

    @Autowired
    private UnixTimeToDate unixTimeToDate;

    @Autowired
    private FinDataMapResolver finDataMapResolver;

    @Override
    public Map<String, String> getNetIncomeData(JsonNode response) {
        Map<String, String> incomeToYearMap = new HashMap<String, String>();       
        statementProcessing.traverseJsonForMap(response, incomeToYearMap, "Net Income");
        return incomeToYearMap;
    }

    @Override
    public Map<String, String> getTotalRevenueData(JsonNode response) {
        Map<String, String> totalRevenueToYearMap = new HashMap<String, String>();
        statementProcessing.traverseJsonForMap(response, totalRevenueToYearMap, "Total Revenue");
        return totalRevenueToYearMap;
    }

    @Override
    public Map<String, String> getEbitdaData(JsonNode response) {
        Map<String, String> ebitdaToYearMap = new HashMap<String, String>();       
        statementProcessing.traverseJsonForMap(response, ebitdaToYearMap, "EBITDA");
        return ebitdaToYearMap;
    }

    @Override
    public Map<String, String> getInterestExpenseData(JsonNode response) {
        Map<String, String> interestExpenseToYearMap = new HashMap<String, String>();       
        statementProcessing.traverseJsonForMap(response, interestExpenseToYearMap, "Interest Expense");
        return interestExpenseToYearMap;
    }

    @Override
    public Map<String, String> getCFOData(JsonNode response) {
        Map<String, String> CFOToYearMap = new HashMap<String, String>();       
        statementProcessing.traverseJsonForMap(response, CFOToYearMap, "Cash Flow From Continuing Investing Activities");
        return CFOToYearMap;
    }

    @Override
    public Map<String, String> getFreeCashFlowData(JsonNode response) {
        Map<String, String> freeCashFlowToYearMap = new HashMap<String, String>();
        statementProcessing.traverseJsonForMap(response, freeCashFlowToYearMap, "Free Cash Flow");
        return freeCashFlowToYearMap;
    }

    @Override
    public Map<String, String> getCapexData(JsonNode response) {
        Map<String, String> capexToYearMap = new HashMap<String, String>();
        statementProcessing.traverseJsonForMap(response, capexToYearMap, "Capital Expenditure");
        return capexToYearMap;
    }

    @Override
    public Map<String, Map<String, String>> unixToDateHistory(Map<String, Map<Long, Double>> historymap) {
        Map<String, Map<String, String>> resultantMap = new HashMap<>();
        for(Map.Entry<String,Map<Long, Double>> entry: historymap.entrySet()){
            String key = entry.getKey();
            Map<Long, Double> valueMap = entry.getValue();
            Map<String, String> updatedMap = new HashMap<>();
            for(Map.Entry<Long, Double> entry2: valueMap.entrySet()){
                Long oldKey = entry2.getKey();
                String newKey = unixTimeToDate.convertToDate(oldKey);
                String value = entry2.getValue().toString();
                updatedMap.put(newKey, value);
            }
            resultantMap.put(key, updatedMap);
        }
        resultantMap = finDataMapResolver.structuredFinData(resultantMap, "volume");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Sort using TreeMap with a custom comparator (sort by date)
        TreeMap<String, Map<String, String>> sortedMap = new TreeMap<>((date1, date2) -> {
            LocalDate localDate1 = LocalDate.parse(date1, formatter);
            LocalDate localDate2 = LocalDate.parse(date2, formatter);
            return localDate1.compareTo(localDate2); // Compare dates
        });
        
        // Put all entries in the sorted TreeMap
        sortedMap.putAll(resultantMap);
        return sortedMap;
    }

    @Override
    public Map<String, String> getEbitData(JsonNode response) {
        Map<String, String> EbitToYearMap = new HashMap<String, String>();
        statementProcessing.traverseJsonForMap(response, EbitToYearMap, "EBIT");
        return EbitToYearMap;
    }

    @Override
    public Map<String, String> getAssetData(JsonNode response) {
        Map<String, String> AssetToYearMap = new HashMap<String, String>();
        statementProcessing.traverseJsonForMap(response, AssetToYearMap, "Total Assets");
        return AssetToYearMap;
    }

    @Override
    public Map<String, String> getLiabilityData(JsonNode response) {
        Map<String, String> LiabilityToYearMap = new HashMap<String, String>();
        statementProcessing.traverseJsonForMap(response, LiabilityToYearMap, "Total Non Current Liabilities Net Minority Interest");
        return LiabilityToYearMap;
    }

}
