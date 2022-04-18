package com.company.calculator.db;

import com.company.calculator.model.HistoryAction;
import com.company.calculator.model.HistoryList;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

public class HistoryHelperImpl implements HistoryHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final Path path;

    public HistoryHelperImpl() {
        URL rootResource = this.getClass().getResource("/data");
        this.path = Paths.get(Objects.requireNonNull(rootResource).getPath(), "histories.json");

        if (!Files.exists(path)) {
            try {
                Files.writeString(path, "{\"histories\": []}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean publishAction(HistoryAction action) {
        try {
            HistoryList historyList = readAllActions().orElse(new HistoryList());
            historyList.add(action);

            String json = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(historyList);

            Files.writeString(path, json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Optional<HistoryList> readAllActions() {
        try {
            String json = Files.readString(path);
            HistoryList historyList = objectMapper.readValue(json, HistoryList.class);
            return Optional.of(historyList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
