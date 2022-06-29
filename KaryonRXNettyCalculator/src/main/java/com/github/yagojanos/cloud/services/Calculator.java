package com.github.yagojanos.cloud.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Calculator {

    private static List<OperationStrategy> log = new ArrayList<>();

    public Calculator(){
    }

    public double getResult(OperationStrategy strategy, double... numbers){

        log.add(strategy);

        double result = strategy.calculate(numbers);

        return result;
    }

    public List<OperationStrategy> getCalculatorLog(){
        return Collections.unmodifiableList(log);
    }

    public void clearLog(){
        log.clear();
    }
}
