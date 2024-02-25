import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class ExampleUsingProperties {
    @Test
    @DisplayName("Пример записи Properties")
    public void exampleWriteProperties(){
        Properties properties = new Properties();
        properties.setProperty("test1", "this example 1");
        properties.setProperty("user", "root");
        properties.setProperty("password", "12345");
        properties.setProperty("poolSize", "5");
        try {
            properties.store(new FileWriter("src/main/resources/app.properties"), "This example properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Пример чтения Properties")
    public void exampleReadProperties(){
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/main/resources/app.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(properties);
    }
}
