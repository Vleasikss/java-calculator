package com.company.calculator.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HistoryList implements Serializable {

    private List<HistoryAction> histories = new ArrayList<>();

    public List<HistoryAction> getHistories() {
        return histories;
    }

    public void setHistories(List<HistoryAction> histories) {
        this.histories = histories;
    }

    public void add(HistoryAction history) {
        this.histories.add(history);
    }
}
