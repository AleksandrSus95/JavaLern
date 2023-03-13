package org.examples.interfaceClass;

public class TriangleAction implements ShapeAction{
    @Override
    public double computePerimeter(AbstractShape shape) {
        double perimeter = 0;
        if (shape instanceof RightTriangle triangle) {
            double a = triangle.getSideA();
            double b = triangle.getSideB();
            perimeter = a + b + Math.hypot(a, b);
        } else {
            throw new IllegalArgumentException("Incompatible shape " + shape.getClass());
        }
        return perimeter;
    }

    @Override
    public double computeSquare(AbstractShape shape) {
        double square;
        if (shape instanceof RightTriangle triangle) {
            square = 1./2 * triangle.getSideA() * triangle.getSideB();
        } else {
            throw new IllegalArgumentException("Incompatible shape " + shape.getClass());
        }
        return square;
    }
}
