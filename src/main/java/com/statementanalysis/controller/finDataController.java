package com.statementanalysis.controller;

import org.springframework.web.bind.annotation.RestController;

import com.statementanalysis.model.FinancialInit;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class finDataController {
    @Autowired
    private FinancialInit financialInit;


    @GetMapping("/data")
    public ResponseEntity<Map<String, Map<String, String>>> getData() {
        return ResponseEntity.ok(financialInit.getFinIndicator()); 
    }

    @GetMapping("/data/other")
    public ResponseEntity<Map<String, Object>> getOtherData(){
        return ResponseEntity.ok(financialInit.getAdditionalData());
    }

    @GetMapping("/data/history")
    public ResponseEntity<Map<String, Map<String, String>>> getHistoryData(){
        return ResponseEntity.ok(financialInit.getUpdatedTimeMap());
    }
    
}
