package com.github.yagojanos.cloud.configuration;

import com.github.yagojanos.cloud.services.Calculator;
import com.github.yagojanos.cloud.services.strategies.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = "com.github.yagojanos.cloud")
public class BeanConfig {

    @Bean(name = "sum")
    @Scope("prototype")
    public AdditionOperation additionOperation(){

        return new AdditionOperation();
    }

    @Bean(name = "sub")
    @Scope("prototype")
    public SubtractionOperation subtractionOperation(){

        return new SubtractionOperation();
    }

    @Bean(name = "mult")
    @Scope("prototype")
    public MultiplicationOperation multiplicationOperation(){

        return new MultiplicationOperation();
    }

    @Bean(name = "div")
    @Scope("prototype")
    public DivisionOperation divisionOperation(){

        return new DivisionOperation();
    }

    @Bean(name = "pow")
    @Scope("prototype")
    public ExponentiationOperation exponentiationOperation(){

        return new ExponentiationOperation();
    }

    @Bean
    public Calculator calculator(){

        return new Calculator();
    }


}
