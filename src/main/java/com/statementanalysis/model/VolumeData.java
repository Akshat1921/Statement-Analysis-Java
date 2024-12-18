package com.statementanalysis.model;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Component
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VolumeData {
    String volume;
    String regularMarketVolume;
    String averageVolume;
    String averageVolume10days;
    String averageDailyVolume10Day;
}
