package org.examples.interfaceClass;

public class TriangleGeneric implements ShapeGeneric<RightTriangle>{
    @Override
    public double computeSquare(RightTriangle shape) {
        return 0.5 * shape.getSideA() * shape.getSideB();
    }

    @Override
    public double computePerimeter(RightTriangle shape) {
        double a = shape.getSideA();
        double b = shape.getSideB();
        return a + b + Math.hypot(a,b);
    }
}
