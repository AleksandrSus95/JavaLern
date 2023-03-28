import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Stream;

public class IOStreamExample {
    @Test
    @DisplayName("Пример ввода из файла")
    public void inputStreamExample(){
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("testData/test.txt");
            int code = inputStream.read();
            System.out.println(code + " char= " + (char) code);
            byte[] arr = new byte[16];
            int numberBytes = inputStream.read(arr);
            System.out.println("numberBytes = " + numberBytes);
            System.out.print("Inputted information - " + Arrays.toString(arr));
            int readCode;
            while ((readCode = inputStream.read()) != -1){
                System.out.print((char) readCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    @DisplayName("Пример чтения и записи файла")
    public void exReadWriteFile(){
        try (
                FileReader in = new FileReader("testData/test.txt");
                FileWriter out = new FileWriter("testData/testOut.txt")
        ){
            int readCode;
            while ((readCode = in.read()) != -1){
                System.out.print((char) readCode);
                out.write(readCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
