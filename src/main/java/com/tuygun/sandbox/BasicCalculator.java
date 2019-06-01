package com.tuygun.sandbox;

public class BasicCalculator {

    public int add(int a, int b) {
        return Math.addExact(a, b);
    }

    public int subtract(int a, int b) {
        return Math.subtractExact(a, b);
    }

    public int multiple(int a, int b) {
        return Math.multiplyExact(a, b);
    }

    public double divide(int a, int b) {
        return (double) a / b;
    }

    public long factorial(int i){
        if(i == 0){
            return 1L;
        } else {
           return i * factorial(i -1);
        }
    }
}
