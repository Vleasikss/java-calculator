package com.company.calculator.db;

import com.company.calculator.model.HistoryAction;

import java.util.List;
import java.util.Optional;

public class RestrictedHistoryStorageHelper extends HistoryStorageHelper {

    private final Optional<String> userId;
    private final HistoryStorageHelper storageHelper;

    public RestrictedHistoryStorageHelper(HistoryStorageHelper storageHelper, Optional<String> userId) {
        super();
        this.userId = userId;
        this.storageHelper = storageHelper;
    }

    @Override
    public List<HistoryAction> findAll() {
        if (userId.isPresent()) {
            return super.findAll();
        }
        System.out.println("You have no access to provide such operations");
        return List.of();
    }

    @Override
    public List<HistoryAction> findByUserId(String id) {
        if (userId.isPresent()) {
            return super.findByUserId(id);
        }
        System.out.println("You have no access to provide such operations");
        return List.of();
    }

    @Override
    public Optional<HistoryAction> findById(String id) {
        return storageHelper.findById(id);
    }

    @Override
    public void save(HistoryAction historyAction) {
        storageHelper.save(historyAction);
    }
}
