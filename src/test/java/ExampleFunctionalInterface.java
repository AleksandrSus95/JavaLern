import org.examples.functionalInterface.DefaultAndStatic;
import org.examples.functionalInterface.MyFunctionalInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ExampleFunctionalInterface {

    @Test
    @DisplayName("Пример использования статических и дефолтных методов интерфейса")
    public void staticAndDefaultMethod() {
        class InterfaceIml implements DefaultAndStatic {

            @Override
            public int define(int x1, int y1) {
                return x1 + y1;
            }

            @Override
            public void load() {
                System.out.println("Load");
            }
        }

        InterfaceIml interfaceIml = new InterfaceIml();
        System.out.println(interfaceIml.define(2, 3));
        interfaceIml.load();//Вызове переопределенного метода
        interfaceIml.anOperator();//Вызов дефолтного метода
        DefaultAndStatic.action();//вызов статического метода
    }

    @Test
    @DisplayName("Примеры использования функциональных интерфейсов")
    public void exampleFunctionalInterface() {
        // В виде классической реализации интерфейса
        class FuncInterfaceImpl implements MyFunctionalInterface {

            @Override
            public double perimeter(double a, double b) {
                return 2 * (a + b);
            }
        }

        System.out.println("Calc Perimeter rectangle 2 and 3: " + new FuncInterfaceImpl().perimeter(2, 3));

        // В виде лямбда вырважения
        MyFunctionalInterface myFunctionalInterface = (double a, double b) -> 2 * ( a + b );
        System.out.println("Calc perimeter rectangle 2 and 3: " + myFunctionalInterface.perimeter(2, 3));

        // В виде параметра метода
        class UsingInterfaceInParam {
            private double x;
            private double y;
            public UsingInterfaceInParam(double x, double y){
                this.x = x;
                this.y = y;
            }

            public double action(MyFunctionalInterface service){
                return 10 * service.perimeter(this.x, this.y);
            }
        }
        // Тогда обращение к интерфейсу будет следующим
        double result = new UsingInterfaceInParam(3,5).action((a,b) -> (a +b) * 4);
        System.out.println(result);
    }

}
