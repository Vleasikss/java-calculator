package com.company.calculator.db;

import com.company.calculator.exception.NoIdProvidedException;
import com.company.calculator.model.HistoryAction;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class HistoryStorageHelper extends AbstractInMemoryStorageHelper<HistoryAction> {

    public HistoryStorageHelper() {
        super();
    }

    @Override
    public String storageFilename() {
        return "histories.json";
    }

    @Override
    protected Class<HistoryAction> clazz() {
        return HistoryAction.class;
    }

    public List<HistoryAction> findByUserId(String id) {
        if (id == null) {
            return List.of();
        }
        return findAll().stream().filter(historyAction -> Objects.equals(id, historyAction.getUserId())).collect(Collectors.toList());
    }

}
