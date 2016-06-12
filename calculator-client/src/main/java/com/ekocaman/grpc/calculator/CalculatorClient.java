package com.ekocaman.grpc.calculator;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public final class CalculatorClient {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ManagedChannel channel;
    private final CalculatorServiceGrpc.CalculatorServiceBlockingClient calculatorServiceBlockingClient;

    public CalculatorClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true) // for debug
                .build();

        calculatorServiceBlockingClient = CalculatorServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public double calculate(double number1, double number2, CalculatorModel.CalculatorRequest.OperationType operationType) {
        log.info("Calculating number1 : {} and number2 : {} with operation : {}", number1, number2, operationType);

        CalculatorModel.CalculatorRequest request = CalculatorModel.CalculatorRequest.newBuilder()
                .setNumber1(number1)
                .setNumber2(number2)
                .setOperation(operationType)
                .build();
        CalculatorModel.CalculatorResponse response;

        try {
            response = calculatorServiceBlockingClient.calculate(request);
        } catch (StatusRuntimeException exc) {
            log.warn("RPC failed : {} ", exc.getStatus());
            throw exc;
        }

        log.info("Calculate Result : {}", response.getResult());

        return response.getResult();
    }

    public static void main(String[] args) throws InterruptedException {
        CalculatorClient client = new CalculatorClient("localhost", 8081);
        try {
            double result = client.calculate(2, 5, CalculatorModel.CalculatorRequest.OperationType.MULTIPLY);
            System.out.println("RESULT : " + result);
        } finally {
            client.shutdown();
        }
    }
}
