import org.examples.interfaceClass.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ExampleUseInterface {
    @Test
    @DisplayName("Свойства ссылок на интерфейс")
    public void exampleInterfaceLink(){
        ShapeAction action;
        try {
            Rectangle rectShape = new Rectangle(2, 5);
            action = new RectangleAction();
            System.out.println("Square rectangle: " + action.computeSquare(rectShape));
            System.out.println("Perimeter rectangle: " + action.computePerimeter(rectShape));
            RightTriangle trShape = new RightTriangle(3, 4);
            action = new TriangleAction();
            System.out.println("Square triangle: " + action.computeSquare(trShape));
            System.out.println("Perimeter triangle: " + action.computePerimeter(trShape));
            action.computePerimeter(rectShape); // runtime exception
        }catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
