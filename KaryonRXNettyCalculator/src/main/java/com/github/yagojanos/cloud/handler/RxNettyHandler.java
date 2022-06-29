package com.github.yagojanos.cloud.handler;

import com.github.yagojanos.cloud.configuration.BeanConfig;
import com.github.yagojanos.cloud.exceptions.DivideByZeroException;
import com.github.yagojanos.cloud.services.Calculator;
import com.github.yagojanos.cloud.services.OperationStrategy;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import netflix.karyon.transport.http.health.HealthCheckEndpoint;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RxNettyHandler implements RequestHandler<ByteBuf, ByteBuf> {

    private final String healthCheckUri;
    private final HealthCheckEndpoint healthCheckEndpoint;

    private final ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);

    public RxNettyHandler(String healthCheckUri, HealthCheckEndpoint healthCheckEndpoint) {
        this.healthCheckUri = healthCheckUri;
        this.healthCheckEndpoint = healthCheckEndpoint;
    }

    @Override
    public rx.Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {

        if (request.getUri().startsWith(healthCheckUri)) {

            return healthCheckEndpoint.handle(request, response);

        } else if (request.getUri().startsWith("/calculate")
                && request.getQueryParameters().containsKey("numbers")
                && request.getQueryParameters().containsKey("operation")) {

            Calculator calculator = context.getBean(Calculator.class);

            var params = request.getQueryParameters();

            String[] numbersParameterAsArrayOfString = params.get("numbers").get(0).split(",");
            double[] numbersParameterAsArrayOfDouble = new double[numbersParameterAsArrayOfString.length];

            String operationParameter = params.get("operation").get(0);

            Double result;

            try {

                for(int i = 0; i < numbersParameterAsArrayOfString.length; i++){
                    numbersParameterAsArrayOfDouble[i] = Double.parseDouble(numbersParameterAsArrayOfString[i]);
                }

                result = calculator.getResult((OperationStrategy) context.getBean(operationParameter), numbersParameterAsArrayOfDouble);

            } catch (NumberFormatException e){

                e.printStackTrace();
                response.setStatus(HttpResponseStatus.BAD_REQUEST);
                return response.writeStringAndFlush("Bad Request, You must enter numbers in 'numbers' query parameter");

            } catch (NoSuchBeanDefinitionException e) {

                e.printStackTrace();
                response.setStatus(HttpResponseStatus.BAD_REQUEST);
                return response.writeStringAndFlush("Invalid operation parameter");

            } catch (DivideByZeroException e){

                e.printStackTrace();
                response.setStatus(HttpResponseStatus.BAD_REQUEST);
                return response.writeStringAndFlush("You can't divide a number by zero");

            } catch (Exception e) {

                e.printStackTrace();
                response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
                return response.writeStringAndFlush("Something goes wrong");

            }

            return response.writeStringAndFlush("Result = " + result);

        } else {

            response.setStatus(HttpResponseStatus.NOT_FOUND);
            return response.writeStringAndFlush("Wrong path");
        }
    }
}
