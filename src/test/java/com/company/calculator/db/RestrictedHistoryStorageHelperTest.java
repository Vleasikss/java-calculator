package com.company.calculator.db;

import com.company.calculator.model.ArithmeticFunction;
import com.company.calculator.model.HistoryAction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class RestrictedHistoryStorageHelperTest {

    private static class TestHistoryStorageHelper extends HistoryStorageHelper {
        @Override
        public String storageFilename() {
            return "test-histories.json";
        }
    }

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldFindAllHistories() throws IOException {
        HistoryAction historyAction = new HistoryAction("1", ArithmeticFunction.MINUS, "1", "5-5=0");
        HistoryStorageHelper historyStorageHelper = new RestrictedHistoryStorageHelper(new TestHistoryStorageHelper(), Optional.of("1"));

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(historyAction);
        Files.writeString(historyStorageHelper.storageFilePath(), json);

        List<HistoryAction> all = historyStorageHelper.findAll();
        assertEquals(all.size(), 1);
        assertEquals(all, List.of(historyAction));
    }

    @Test
    public void shouldRestrictAccessToFindAllHistoriesOnEmptyUser() throws IOException {
        HistoryAction historyAction = new HistoryAction("1", ArithmeticFunction.MINUS, "1", "5-5=0");
        HistoryStorageHelper historyStorageHelper = new RestrictedHistoryStorageHelper(new TestHistoryStorageHelper(), Optional.empty());

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(historyAction);
        Files.writeString(historyStorageHelper.storageFilePath(), json);

        List<HistoryAction> all = historyStorageHelper.findAll();
        assertEquals(all.size(), 0);
    }

    @Test
    public void shouldFindHistoryByUserId() throws IOException {
        HistoryAction historyAction = new HistoryAction("1", ArithmeticFunction.MINUS, "1", "5-5=0");
        HistoryStorageHelper historyStorageHelper = new RestrictedHistoryStorageHelper(new TestHistoryStorageHelper(), Optional.of("1"));

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(historyAction);
        Files.writeString(historyStorageHelper.storageFilePath(), json);

        List<HistoryAction> all = historyStorageHelper.findByUserId("1");
        assertEquals(all.size(), 1);
    }

    @Test
    public void shouldRestrictAccessToFindHistoryByUserIdOnEmptyUser() throws IOException {
        HistoryAction historyAction = new HistoryAction("1", ArithmeticFunction.MINUS, "1", "5-5=0");
        HistoryStorageHelper historyStorageHelper = new RestrictedHistoryStorageHelper(new TestHistoryStorageHelper(), Optional.empty());

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(historyAction);
        Files.writeString(historyStorageHelper.storageFilePath(), json);

        List<HistoryAction> all = historyStorageHelper.findByUserId("1");
        assertEquals(all.size(), 0);
    }

    @Before
    public void beforeEach() throws IOException {
        HistoryStorageHelper historyStorageHelper = new TestHistoryStorageHelper();
        Files.delete(historyStorageHelper.storageFilePath());
        historyStorageHelper.init();

    }

    @AfterClass
    public static void afterAll() throws IOException {
        HistoryStorageHelper historyStorageHelper = new TestHistoryStorageHelper();
        Files.delete(historyStorageHelper.storageFilePath());
    }
}
