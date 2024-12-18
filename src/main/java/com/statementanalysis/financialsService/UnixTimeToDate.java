package com.statementanalysis.financialsService;

import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.stereotype.Component;

@Component
public class UnixTimeToDate {
    public String convertToDate(Long unixTime){
        Date date = new Date(unixTime);
        SimpleDateFormat jdf = new SimpleDateFormat("dd-MM-yyyy");
        return jdf.format(date);
    }
}
