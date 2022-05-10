package com.company.calculator.service.printer;

import com.company.calculator.db.HistoryStorageHelper;
import com.company.calculator.exception.NoAccessException;
import com.company.calculator.model.HistoryAction;

import java.util.List;
import java.util.stream.Collectors;

public class HistoryLogPrinterService implements PrinterService {

    private static final HistoryStorageHelper historyStorageHelper = new HistoryStorageHelper();
    private static final String HISTORY_INTRODUCTION = "HISTORY FOR USER: ";

    public void print(String userId) {
        if (userId == null) {
            throw new NoAccessException();
        }
        List<HistoryAction> byUserId = historyStorageHelper.findByUserId(userId);
        String introduction = historyIntroduction(userId);
        String descriptions = byUserId.stream().map(HistoryAction::getDesc).collect(Collectors.joining(",\n"));
        System.out.println(introduction + descriptions);
    }

    private static String historyIntroduction(String userId) {
        return HISTORY_INTRODUCTION + userId + "\n";
    }

}
