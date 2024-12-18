package com.statementanalysis.model;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Component
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceData {
    String fiftyTwoWeekLow;
    String fiftyTwoWeekHigh;
    String twoHundredDayAverage;
    String fiftyDayAverage;
}
