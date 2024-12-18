package com.statementanalysis.financialsService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FinDataMapResolver {

    public Map<String, Map<String, String>> structuredFinData(Map<String, Map<String, String>> finMap, String key){
        Map<String, String> yearMap = new HashMap<>();
        yearMap = finMap.get(key);

        ArrayList<String> yearList = new ArrayList<>();
        ArrayList<String> finDataList = new ArrayList<>();
        
        finDataList = (ArrayList<String>) finMap.keySet().stream().collect(Collectors.toList());

        createYearList(yearList, yearMap);
        Collections.sort(yearList);
        Map<String, Map<String, String>> finalMap = createFinDataMap(finDataList, yearList, finMap);
        return finalMap;

    }

    private Map<String, Map<String, String>> createFinDataMap(ArrayList<String> finDataList, ArrayList<String> yearList, Map<String, Map<String, String>> finMap) {

        Map<String, Map<String, String>> res = new HashMap<>();

        for(String year : yearList){
            Map<String, String> temp = mapYearToFinData(year, finMap, finDataList);
            res.put(year, temp);
        }
        return res;
    }

    private Map<String, String> mapYearToFinData(String year, Map<String, Map<String, String>> finMap, ArrayList<String> finDataList) {
        Map<String, String> res = new HashMap<>();
        for(String finData: finDataList){
            Map<String, String> temp = finMap.get(finData);
            temp.forEach((key, value)->{
                if(key.equals(year)){
                    res.put(finData, value);
                }
            });
        }
        return res;
    }

    void createYearList(ArrayList<String> yearList, Map<String, String> statementMap){
        for (Map.Entry<String, String> entry2 : statementMap.entrySet()){
            if(!entry2.getKey().equals("2019")){
                yearList.add(entry2.getKey());
            }
        }
    }

}
