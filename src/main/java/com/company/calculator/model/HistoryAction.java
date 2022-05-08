package com.company.calculator.model;

public class HistoryAction implements EntityId {

    private String id;
    private MathFunction function;
    private String desc;
    private String userId;

    public HistoryAction() {
    }

    public HistoryAction(MathFunction action, String userId, String desc) {
        this.function = action;
        this.userId = userId;
        this.desc = desc;
    }

    public HistoryAction(String id, MathFunction action, String userId, String desc) {
        this.id = id;
        this.function = action;
        this.userId = userId;
        this.desc = desc;
    }

    public static String buildDescription(String expression, double result) {
        return expression + "=" + result;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MathFunction getFunction() {
        return function;
    }

    public void setFunction(MathFunction function) {
        this.function = function;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "HistoryAction{" +
                "id='" + id + '\'' +
                ", function=" + function +
                ", desc='" + desc + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
