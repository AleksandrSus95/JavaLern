package org.examples.interfaceClass;

public class RectangleGeneric implements ShapeGeneric<Rectangle>{
    @Override
    public double computeSquare(Rectangle shape) {
        return shape.getHeight() * shape.getWidth();
    }

    @Override
    public double computePerimeter(Rectangle shape) {
        return 2 * (shape.getWidth() + shape.getHeight());
    }
}
