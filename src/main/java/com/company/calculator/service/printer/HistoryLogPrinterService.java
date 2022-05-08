package com.company.calculator.service.printer;

import com.company.calculator.db.HistoryStorageHelper;
import com.company.calculator.model.HistoryAction;

import java.util.List;
import java.util.stream.Collectors;

public class HistoryLogPrinterService implements PrinterService {

    private static final HistoryStorageHelper storageHelper = new HistoryStorageHelper();
    private static final String HISTORY_INTRODUCTION = "HISTORY FOR USER: ";

    public void print(String userId) {
        List<HistoryAction> byUserId = storageHelper.findByUserId(userId);
        String introduction = historyIntroduction(userId);
        String descriptions = byUserId.stream().map(HistoryAction::getDesc).collect(Collectors.joining(",\n"));
        System.out.println(introduction + descriptions);
    }

    private static String historyIntroduction(String userId) {
        return HISTORY_INTRODUCTION + userId + "\n";
    }

}
