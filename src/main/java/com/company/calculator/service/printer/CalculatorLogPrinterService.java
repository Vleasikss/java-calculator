package com.company.calculator.service.printer;

import com.company.calculator.db.HistoryStorageHelper;
import com.company.calculator.model.HistoryAction;

import java.util.Optional;

public class CalculatorLogPrinterService implements PrinterService {

    private final HistoryStorageHelper storageHelper;

    public CalculatorLogPrinterService(HistoryStorageHelper storageHelper) {
        this.storageHelper = storageHelper;
    }

    @Override
    public void print(String identifier) {
        Optional<HistoryAction> byId = storageHelper.findById(identifier);
        byId.ifPresent(historyAction -> System.out.println(historyAction.getDesc()));
    }
}
