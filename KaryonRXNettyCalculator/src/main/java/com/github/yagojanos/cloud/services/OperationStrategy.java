package com.github.yagojanos.cloud.services;

import com.github.yagojanos.cloud.services.strategies.enums.OperationType;

import java.util.List;

public interface OperationStrategy {

    double calculate(double... nums);

    OperationType getOperationType();
    List<Double> getOperatedNumbers();
}
