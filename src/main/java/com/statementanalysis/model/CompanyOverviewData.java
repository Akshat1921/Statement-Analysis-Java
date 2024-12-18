package com.statementanalysis.model;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Component
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyOverviewData {
    String address1;
    String city;
    String state;
    String zip;
    String country;
    String phone;
    String website;
    String industry;
    String industryKey;
    String industryDisp;
    String sector;
    String sectorKey;
    String sectorDisp;
    String longBusinessSummary;
}
