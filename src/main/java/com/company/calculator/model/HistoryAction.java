package com.company.calculator.model;

public class HistoryAction {

    private String id;
    private Action action;

    public HistoryAction() {
    }

    public HistoryAction(String id, Action action) {
        this.id = id;
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

}
