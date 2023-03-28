import org.examples.exceptionReadme.ThrowableExample;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FinallyExample {
    @Test
    @DisplayName("Example Using finally block")
    public void exampleFinally(){
        class Resource {

            public boolean exists() {
                return false;
            }

            public boolean isCreate() {
                return false;
            }
        }
        class ResourceAction {
            public static void load(Resource resource) throws ThrowableExample{
                if (resource == null || !resource.exists() || !resource.isCreate()) {
                    throw new ThrowableExample("Some message");
                }
            }
        }
        class ExClass {
            public void doAction(){
                Resource resources = null;
                try {
                    resources = new Resource();
                    ResourceAction.load(resources);
                }catch (ThrowableExample e){
                    System.out.println("This exception " + e.getMessage() + " " + e.getCause());
                }finally {
                    if(resources != null){
                        System.out.println("Resource close");
                    }
                }
                System.out.println("After finally");
            }

            public String exFinallyReturn(){
                try {
                    System.out.println("This try block");
                    return "Data from try block";
                }finally {
                    System.out.println("This finally block");
                    return "Data from finally block";
                }
            }

            public String exFinallyReturnEx2(){
                try {
                    System.out.println("This try block");
                    return "Data from try block";
                }finally {
                    System.out.println("This finally block");
                }
            }
        }

        ExClass exClass = new ExClass();
        exClass.doAction();
        System.out.println(exClass.exFinallyReturn());
        System.out.println(exClass.exFinallyReturnEx2());
    }
}
