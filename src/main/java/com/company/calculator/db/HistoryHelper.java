package com.company.calculator.db;

import com.company.calculator.model.Action;
import com.company.calculator.model.HistoryAction;

public interface HistoryHelper {

    boolean publishAction(HistoryAction action);

}
