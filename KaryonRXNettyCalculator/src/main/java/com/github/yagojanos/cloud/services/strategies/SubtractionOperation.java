package com.github.yagojanos.cloud.services.strategies;

import com.github.yagojanos.cloud.services.OperationStrategy;
import com.github.yagojanos.cloud.services.strategies.enums.OperationType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubtractionOperation implements OperationStrategy {

    private List<Double> operatedNumbers;

    @Override
    public double calculate(double... numbers) {

        if(numbers.length == 0){
            throw new IllegalArgumentException("You must pass at least one argument");
        }

        double result = numbers[0];

        for(int i = 1; i < numbers.length; i++){
            result = result - numbers[i];
        }

        Double[] wrapperArrayOfNumbers = convertToWrapperArray(numbers);
        operatedNumbers = Arrays.asList(wrapperArrayOfNumbers);

        return result;
    }

    private Double[] convertToWrapperArray(double[] numbers) {
        Double[] wrappedArrayOfNumbers = new Double[numbers.length];
        Arrays.setAll(wrappedArrayOfNumbers, i -> numbers[i]);
        return wrappedArrayOfNumbers;
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.SUBTRACTION;
    }

    @Override
    public List<Double> getOperatedNumbers() {
        return Collections.unmodifiableList(operatedNumbers);
    }
}
