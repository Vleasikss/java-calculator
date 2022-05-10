package com.company.calculator.service.printer;

import com.company.calculator.exception.NoAccessException;
import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class HistoryLogPrinterServiceTest {

    @Test
    public void shouldPrintLogWithNoExceptions() {
        HistoryLogPrinterService service = new HistoryLogPrinterService();
        service.print("1");
    }

    @Test
    public void shouldThrowExceptionIfNoIdProvided() {
        HistoryLogPrinterService service = new HistoryLogPrinterService();
        assertThrows(NoAccessException.class, () -> service.print(null));
    }
}
