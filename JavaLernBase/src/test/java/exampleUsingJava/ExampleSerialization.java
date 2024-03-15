package exampleUsingJava;

import org.examples.serialixationClass.StudentSerialization;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class ExampleSerialization {
    @Test
    @DisplayName("Запись сериализованного объекта в файл")
    public void writeSerializationObjectInFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("testDir/exSerial.txt"))) {
            StudentSerialization student = new StudentSerialization("Aleksandr", 123456, "testPassword");
            System.out.println(student);
            outputStream.writeObject(student);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Десириализация объекта из файла")
    public void readSerializationObjectFromFile() {
        StudentSerialization.faculty = "Some_Faculty";
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("testDir/exSerial.txt"))) {
            StudentSerialization studentSerialization = (StudentSerialization) inputStream.readObject();
            System.out.println(studentSerialization);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Сериализация объекта в XML")
    public void exampleSerializationObjectInXML(){
        // Сериализация в файл XML
        try (XMLEncoder xmlEncoder = new XMLEncoder(
                new BufferedOutputStream(
                        new FileOutputStream("testDir/serial.xml")))) {
            StudentSerialization studentSerialization = new StudentSerialization("Aleksandr",654321, "testPassword");
            xmlEncoder.writeObject(studentSerialization);
            xmlEncoder.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Десиарилизация из XML
        try (XMLDecoder xmlDecoder = new XMLDecoder(
                new BufferedInputStream(
                        new FileInputStream("testDir/serial.xml")))){
            StudentSerialization studentSerialization = (StudentSerialization) xmlDecoder.readObject();
            System.out.println(studentSerialization);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
