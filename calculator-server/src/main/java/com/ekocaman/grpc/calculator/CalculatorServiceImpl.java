package com.ekocaman.grpc.calculator;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public final class CalculatorServiceImpl implements CalculatorServiceGrpc.CalculatorService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void calculate(CalculatorModel.CalculatorRequest request, StreamObserver<CalculatorModel.CalculatorResponse> responseObserver) {
        log.info("Calculating request : {}", request);

        BigDecimal number1 = BigDecimal.valueOf(request.getNumber1());
        BigDecimal number2 = BigDecimal.valueOf(request.getNumber2());
        BigDecimal result = null;
        switch (request.getOperation()) {
            case ADD:
                result = number1.add(number2);
                break;

            case SUBTRACT:
                result = number1.subtract(number2);
                break;

            case MULTIPLY:
                result = number1.multiply(number2);
                break;

            case DIVIDE:
                result = number1.divide(number2);
                break;

            default:
                responseObserver.onError(new IllegalArgumentException("Operation Type is undefined"));
        }

        if (result != null) {
            responseObserver.onNext(CalculatorModel.CalculatorResponse.newBuilder().setResult(result.doubleValue()).build());
            responseObserver.onCompleted();
        }
    }
}
