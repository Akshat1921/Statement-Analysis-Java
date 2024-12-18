package com.statementanalysis.model;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Component
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdditionalIndicators {
    String bookValue;
    String priceToBook;
    String trailingEps;
    String forwardEps;
    String pegRatio;
    String totalCash;
    String currentRatio;
    String quickRatio;
    String beta;
    String forwardPE;
    String trailingPE;
    String enterpriseValue;
    String ebitda;
    String earningsGrowth;
    String marketCap;
    String operatingCashflow;
}
