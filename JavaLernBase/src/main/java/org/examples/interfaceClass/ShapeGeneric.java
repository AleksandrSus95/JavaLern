package org.examples.interfaceClass;

public interface ShapeGeneric <T extends AbstractShape>{
    double computeSquare(T shape);
    double computePerimeter(T shape);
}
