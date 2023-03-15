import org.examples.annotationClass.example.AccountManager;
import org.examples.annotationClass.example.AccountManagerImpl;
import org.examples.annotationClass.example.SecurityFactory;
import org.examples.annotationClass.simpleExample.Base;
import org.junit.jupiter.api.Test;

public class ExampleUsingAnnotation {
    @Test
    public void usingSimpleAnnotation(){
        Base base = new Base();
        base.doAction();
    }

    @Test
    public void exampleUsingAnnotation(){
        AccountManager manager = SecurityFactory.registerSecurityObject(new AccountManagerImpl());
        manager.depositInCash(10128336, 6);
        manager.withdraw(64092376, 2);
        manager.convert(200);
        manager.transfer(64092376, 300);
    }
}
