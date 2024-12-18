package com.statementanalysis.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class History {
    private String Open;
    private String High;
    private String Low;
    private String Close;
    private String Volume;
    private String Dividends;

}
