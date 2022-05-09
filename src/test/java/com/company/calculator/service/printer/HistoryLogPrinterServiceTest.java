package com.company.calculator.service.printer;

import org.junit.Test;

public class HistoryLogPrinterServiceTest {

    @Test
    public void shouldPrintLogWithNoExceptions() {
        HistoryLogPrinterService service = new HistoryLogPrinterService();
        service.print("1");
    }
}
