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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HistoryStorageHelperTest {

    private static final HistoryStorageHelper historyStorageHelper = new TestHistoryStorageHelper();
    private final ObjectMapper mapper = new ObjectMapper();

    private static class TestHistoryStorageHelper extends HistoryStorageHelper {
        @Override
        public String storageFilename() {
            return "test-histories.json";
        }
    }

    @Test
    public void shouldSaveHistoryAction() throws IOException {
        HistoryAction historyAction = new HistoryAction("1", ArithmeticFunction.MINUS, "1", "5-5=0");

        historyStorageHelper.save(historyAction);

        String s = Files.readString(historyStorageHelper.storageFilePath());
        List<HistoryAction> list = mapper.readValue(s, historyStorageHelper.listClazz());
        assertEquals(list.size(), 1);
        assertEquals(historyAction, list.get(0));
    }

    @Test
    public void shouldFindHistoryActionById() throws IOException {
        HistoryAction historyAction = new HistoryAction("1", ArithmeticFunction.MINUS, "1", "5-5=0");

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(historyAction);
        Files.writeString(historyStorageHelper.storageFilePath(), json);


        HistoryAction byId = historyStorageHelper.findById("1").orElse(null);
        assertNotNull(byId);
        assertEquals(byId, historyAction);
    }

    @Test
    public void shouldFindHistoryActionsByUserId() {
        List<HistoryAction> actions = List.of(
                new HistoryAction("1", ArithmeticFunction.MINUS, "1", "5-5=0"),
                new HistoryAction("2", ArithmeticFunction.PLUS, "1", "5+5=10")
        );

        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(actions);
            Files.writeString(historyStorageHelper.storageFilePath(), json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<HistoryAction> byUserId = historyStorageHelper.findByUserId("1");
        assertNotNull(byUserId);
        assertEquals(byUserId, actions);

    }

    @Before
    public void beforeEach() throws IOException {
        Files.delete(historyStorageHelper.storageFilePath());
        historyStorageHelper.init();
    }

    @AfterClass
    public static void afterAll() throws IOException {
        Files.delete(historyStorageHelper.storageFilePath());
    }
}
