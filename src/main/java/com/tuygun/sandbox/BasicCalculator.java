package com.tuygun.sandbox;

public class BasicCalculator {

    private BasicCalculator(){
    }

    public static int add(int a, int b) {
        return Math.addExact(a, b);
    }

    public static int subtract(int a, int b) {
        return Math.subtractExact(a, b);
    }

    public static int multiple(int a, int b) {
        return Math.multiplyExact(a, b);
    }

    public static double divide(int a, int b) {
        return (double) a / b;
    }

    public static long factorial(int i){
        if(i == 0){
            return 1L;
        } else {
           return i * factorial(i -1);
        }
    }
}
