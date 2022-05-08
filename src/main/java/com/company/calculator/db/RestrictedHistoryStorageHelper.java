package com.company.calculator.db;

import com.company.calculator.model.HistoryAction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RestrictedHistoryStorageHelper extends HistoryStorageHelper {

    private final Optional<String> userId;
    private final HistoryStorageHelper storageHelper;

    public RestrictedHistoryStorageHelper(HistoryStorageHelper storageHelper, Optional<String> userId) {
        this.userId = userId;
        this.storageHelper = storageHelper;
        this.init();
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
        if (historyAction.getId() == null || historyAction.getId().isEmpty()) {
            historyAction.setId(UUID.randomUUID().toString().substring(0, 6));
        }
        storageHelper.save(historyAction);
    }
}
