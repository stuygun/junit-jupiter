package com.tuygun.sandbox.jupiter;

public class MathematicalOperation {
    public enum Operation {
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE,
        FACTORIAL
    }

    private final int a;
    private final int b;
    private final Operation operation;
    private final int result;
    private final double divisionResult;

    public MathematicalOperation(int a, int b, Operation operation, int result) {
        this.a = a;
        this.b = b;
        this.operation = operation;
        this.result = result;
        this.divisionResult = 0.0;
    }

    public MathematicalOperation(int a, int b, Operation operation, double divisionResult) {
        this.a = a;
        this.b = b;
        this.operation = operation;
        this.result = 0;
        this.divisionResult = divisionResult;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public Operation getOperation() {
        return operation;
    }

    public int getResult() {
        return result;
    }

    public double getDivisionResult() {
        return divisionResult;
    }

    @Override
    public String toString() {
        return "MathematicalOperation{" +
                "a=" + a +
                ", b=" + b +
                ", operation=" + operation +
                ", result=" + result +
                ", divisionResult=" + divisionResult +
                '}';
    }
}
