package com.company.calculator.service.printer;

import com.company.calculator.db.HistoryStorageHelper;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;

public class CalculatorLogPrinterServiceTest {

    private static final TestHistoryStorageHelper historyStorageHelper = new TestHistoryStorageHelper();

    private static class TestHistoryStorageHelper extends HistoryStorageHelper {
        @Override
        public String storageFilename() {
            return "test-histories.json";
        }

        public void deleteAll() throws IOException {
            Files.delete(historyStorageHelper.storageFilePath());
            historyStorageHelper.init();
        }
    }

    @Test
    public void shouldPrintLogWithNoExceptions() {
        CalculatorLogPrinterService logPrinterService = new CalculatorLogPrinterService(historyStorageHelper);
        logPrinterService.print("someHistory");
    }

    @AfterClass
    public static void afterAll() throws IOException {
        historyStorageHelper.deleteAll();
    }

}
