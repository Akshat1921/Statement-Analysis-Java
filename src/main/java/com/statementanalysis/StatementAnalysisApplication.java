package com.statementanalysis;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.statementanalysis.financialsService.FinDataMapResolver;
import com.statementanalysis.financialsService.FinancialDataServiceImpl;
import com.statementanalysis.model.AdditionalIndicators;
import com.statementanalysis.model.CompanyOverviewData;
import com.statementanalysis.model.DividendData;
import com.statementanalysis.model.FinancialInit;
import com.statementanalysis.model.PriceData;
import com.statementanalysis.model.RiskData;
import com.statementanalysis.model.ShareData;
import com.statementanalysis.model.VolumeData;
import com.statementanalysis.utils.Utils;

@SpringBootApplication
@EnableCaching
public class StatementAnalysisApplication implements CommandLineRunner{
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FinDataMapResolver finDataMapResolver; 

    @Autowired
    private FinancialInit financialInit;

    @Autowired
    private FinancialDataServiceImpl financialDataService;

    @Autowired
    private Utils utils;


	public static void main(String[] args) {
		SpringApplication.run(StatementAnalysisApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        // Map<String, Object> reposnseMap =  restTemplate.getForObject(url, Map.class);
        // System.out.println(reposnseMap);
        for (String arg : args) {
            System.out.println("EBAY..............." + arg);
        }

        String ticker = "NVDA";
        // System.out.println(ticker);
        String financialsUrl = "http://127.0.0.1:8000/stocks/financials/ticker?ticker=" + ticker;

        String historyUrl = "http://127.0.0.1:8000/stocks/history/ticker?ticker=" + ticker;

        ObjectMapper mapper = new ObjectMapper();

        JsonNode historyJsonNode =  restTemplate.getForObject(historyUrl, JsonNode.class);
        // Map<String, Map<String, String>> res = restTemplate.getForObject(url, Map.class);
        // System.out.println(res);
        
        JsonNode jsonNode =  restTemplate.getForObject(financialsUrl, JsonNode.class);

        if (historyJsonNode == null || jsonNode == null) {
            throw new RuntimeException("Failed to fetch financial data from the URL: " + financialsUrl);
        }

        Map<String, Map<String, String>> finData = new HashMap<>();

        JsonNode incomeStatementNode = jsonNode.path("income-statement");
        JsonNode balanaceSheetNode = jsonNode.path("balance-sheet");
        JsonNode cashFlowNode = jsonNode.path("cashflow");

        // CFO
        Map<String, String> cfo = financialDataService.getCFOData(cashFlowNode);
        // Income Statement
        Map<String, String> income = financialDataService.getNetIncomeData(incomeStatementNode);
        // Total Revenue
        Map<String, String> revenue = financialDataService.getTotalRevenueData(incomeStatementNode);
        // Free CashFlow
        Map<String, String> freeCashFlow = financialDataService.getFreeCashFlowData(cashFlowNode);
        // Capex
        Map<String, String> capex = financialDataService.getCapexData(cashFlowNode);
        // interestExpense
        Map<String, String> interest = financialDataService.getInterestExpenseData(incomeStatementNode);
        // Ebitda
        Map<String, String> ebitda = financialDataService.getEbitdaData(incomeStatementNode);
        // total assets
        Map<String, String> assets = financialDataService.getAssetData(balanaceSheetNode);
        // ebit
        Map<String, String> ebit = financialDataService.getEbitData(incomeStatementNode);
        // liability
        Map<String, String> liability = financialDataService.getLiabilityData(balanaceSheetNode);

        Object companyOverviewNode = jsonNode.path("company-overview");
        
        CompanyOverviewData companyOverviewData = mapper.convertValue(companyOverviewNode, CompanyOverviewData.class); 
        RiskData riskData = mapper.convertValue(companyOverviewNode, RiskData.class);
        AdditionalIndicators additionalIndicatorsData = mapper.convertValue(companyOverviewNode, AdditionalIndicators.class);
        DividendData dividendData = mapper.convertValue(companyOverviewNode, DividendData.class);
        PriceData priceData = mapper.convertValue(companyOverviewNode, PriceData.class);
        ShareData shareData = mapper.convertValue(companyOverviewNode, ShareData.class);
        VolumeData volumeData = mapper.convertValue(companyOverviewNode, VolumeData.class);

        JsonNode combinedHistorynode = historyJsonNode.get("combined-history");

        JsonNode openNode = combinedHistorynode.get("Open");
        JsonNode CloseNode = combinedHistorynode.get("Close");
        JsonNode HighNode = combinedHistorynode.get("High");
        JsonNode LowNode = combinedHistorynode.get("Low");
        JsonNode VolumeNode = combinedHistorynode.get("Volume");

        Map<Long, Double> openMap = new HashMap<>();
        Map<Long, Double> closeMap = new HashMap<>();
        Map<Long, Double> highMap = new HashMap<>();
        Map<Long, Double> lowMap = new HashMap<>();
        Map<Long, Double> volumeMap = new HashMap<>();

        utils.extractHistoryData(VolumeNode, volumeMap);
        utils.extractHistoryData(openNode, openMap);
        utils.extractHistoryData(CloseNode, closeMap);
        utils.extractHistoryData(HighNode, highMap);
        utils.extractHistoryData(LowNode, lowMap);
        
        Map<String,Map<Long,Double>> historymap = new HashMap<>();
        historymap.put("volume", volumeMap);
        historymap.put("open", openMap);
        historymap.put("high", highMap);
        historymap.put("low", lowMap);
        historymap.put("close", closeMap);

        // System.out.println(historymap);
        financialInit.setHistoryDatMap(historymap);

        Map<String, Object> currentFundamentals = new HashMap<>();

        currentFundamentals.put("companyOverview", companyOverviewData);
        currentFundamentals.put("riskData", riskData);
        currentFundamentals.put("additionalData", additionalIndicatorsData);
        currentFundamentals.put("dividendData", dividendData);
        currentFundamentals.put("volumeData", volumeData);
        currentFundamentals.put("shareData", shareData);
        currentFundamentals.put("priceData", priceData);



        finData.put("income", income);
        finData.put("revenue", revenue);
        finData.put("cfo", cfo);
        finData.put("freeCashFlow", freeCashFlow);
        finData.put("capex", capex);
        finData.put("interest", interest);
        finData.put("ebitda", ebitda);
        finData.put("ebit", ebit);
        finData.put("assets", assets);
        finData.put("liability", liability);
        

        Map<String, Map<String, String>> finDataRes = finDataMapResolver.structuredFinData(finData, "income");
        financialInit.setFinIndicator(finDataRes);
        financialInit.setAdditionalData(currentFundamentals);
        financialInit.setUpdatedTimeMap(financialDataService.unixToDateHistory(historymap));
    }
}
