package com.github.yagojanos.cloud;


import com.github.yagojanos.cloud.configuration.BeanConfig;
import com.github.yagojanos.cloud.exceptions.DivideByZeroException;
import com.github.yagojanos.cloud.services.Calculator;
import com.github.yagojanos.cloud.services.OperationStrategy;
import com.github.yagojanos.cloud.services.strategies.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;


@ContextConfiguration(classes = {BeanConfig.class})
@ExtendWith(SpringExtension.class)
public class CalculatorTest {

    @Autowired
    private Calculator calculator;

    @Autowired
    private AdditionOperation additionOperation;

    @Autowired
    private SubtractionOperation subtractionOperation;

    @Autowired
    private MultiplicationOperation multiplicationOperation;

    @Autowired
    private DivisionOperation divisionOperation;

    @Autowired
    private ExponentiationOperation exponentiationOperation;

    public CalculatorTest() {
    }

    @AfterEach
    public void afterSetup(){
        calculator.clearLog();
    }

    @Test
    public void mustAdd(){
        var returnedValue = calculator.getResult(additionOperation, 2, 2);

        Assertions.assertEquals(4, returnedValue);
    }

    @Test
    public void mustSubtract(){
        var returnedValue = calculator.getResult(subtractionOperation, 2, 2);

        Assertions.assertEquals(0, returnedValue);
    }

    @Test
    public void mustMultiply(){
        var returnedValue = calculator.getResult(multiplicationOperation, 2, 3);

        Assertions.assertEquals(6, returnedValue);
    }

    @Test
    public void mustDivide(){
        var returnedValue = calculator.getResult(divisionOperation, 6, 2);

        Assertions.assertEquals(3, returnedValue);
    }

    @Test
    public void mustPower(){
        var returnedValue = calculator.getResult(exponentiationOperation, 6, 2);

        Assertions.assertEquals(36, returnedValue);
    }

    @Test
    public void mustThrowDivideByZeroException(){

        DivideByZeroException thrown = Assertions.assertThrows(DivideByZeroException.class, () ->
            calculator.getResult(divisionOperation, 6,0)
        );

        Assertions.assertEquals("You can't divide a number by zero", thrown.getMessage());
    }

    @Test
    public void mustReturnLogList(){
        calculator.getResult(additionOperation, 2, 2);
        calculator.getResult(subtractionOperation, 2, 2);
        calculator.getResult(multiplicationOperation, 2, 2);
        calculator.getResult(divisionOperation, 2, 2);
        calculator.getResult(exponentiationOperation, 2, 2);

        List<OperationStrategy> listThatShouldBeReturned = Arrays.asList(additionOperation, subtractionOperation, multiplicationOperation, divisionOperation, exponentiationOperation);

        var log = calculator.getCalculatorLog();

        System.out.println(log.get(0));

        Assertions.assertAll(
                () -> Assertions.assertEquals(listThatShouldBeReturned, log),
                () -> Assertions.assertTrue(listThatShouldBeReturned.size() == log.size())
        );
    }
}
