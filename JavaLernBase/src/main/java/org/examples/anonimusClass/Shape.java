package org.examples.anonimusClass;

import java.util.StringJoiner;

public enum Shape {
    RECTANGLE(2, 3) {
        public double computeSquare() {
            return this.getA() * this.getB();
        }
    },
    TRIANGLE(2, 3) {
        public double computeSquare() {
            return this.getA() * this.getB() / 2;
        }
    };

    private double a;
    private double b;

    Shape(double a, double b){
        this.a = a;
        this.b = b;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public abstract double computeSquare();

    @Override
    public String toString() {
        return  new StringJoiner(",", Shape.class.getSimpleName() + "[", "]" )
                .add("a=" + a).add("b=" + b).toString();
    }
}
