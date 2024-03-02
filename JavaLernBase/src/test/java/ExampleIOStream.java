import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExampleIOStream {
    @Test
    @DisplayName("Пример ввода из файла")
    public void inputStreamExample() {
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
            while ((readCode = inputStream.read()) != -1) {
                System.out.print((char) readCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
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
    public void exReadWriteFile() {
        try (
                FileReader in = new FileReader("testData/test.txt");
                FileWriter out = new FileWriter("testData/testOut.txt")
        ) {
            int readCode;
            while ((readCode = in.read()) != -1) {
                System.out.print((char) readCode);
                out.write(readCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Пример работы с File, Path")
    public void exWorkWithFilePath() {
        File file = new File("testData/test.txt"); //Объект файл
        Path path = file.toPath(); // Получение Path из Файла
        System.out.println(path);
        path = Paths.get("testData/test.txt"); // Получения Path прямой инициализацией
        System.out.println(path);
        path = FileSystems.getDefault().getPath("testData/test.txt");
        System.out.println(path);
        // FileSystems - определяет набор статических методов для получения и создания файловых систем
        // Пример поиска всех файлов с расширение .java из папки src
        path = FileSystems.getDefault().getPath("src");
        if (Files.exists(path) && Files.isDirectory(path)) {
            try (Stream<Path> streamDir = Files.find(path, 10, (p, a) -> String.valueOf(p).endsWith(".java"))) {
                long counter = streamDir.map(String::valueOf)
                        .peek(System.out::println)
                        .count();
                System.out.println("found: " + counter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    @DisplayName("Пример работы с файловой системой")
    public void exWorkFileSystem() {
        File file = new File("testDir" + File.separator + "info.txt");
        if (file.exists() && file.isFile()) {
            System.out.println("Path: \t" + file.getPath());
            System.out.println("Absolute Path: \t" + file.getAbsolutePath());
            System.out.println("Size: \t" + file.length());
            System.out.println("Dir: \t" + file.getParent());
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File dir = new File("testDir");
        if (dir.exists() && dir.isDirectory()) {
            for (File current : dir.listFiles()) {
                long millis = current.lastModified();
                Instant date = Instant.ofEpochMilli(millis);
                System.out.println(current.getPath() + "\t" + current.length() + "\t" + date);
            }
        }
        File root = File.listRoots()[0];
        System.out.printf("\n%s %,d from %,d free bytes", root.getPath(), root.getUsableSpace(),
                root.getTotalSpace());
    }

    @Test
    @DisplayName("Чтение строки из файла")
    public void readLineExample() {
        StringBuilder stringLine = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("testData/test.txt"))) {
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                stringLine.append(tmp).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Чтение из файла построково");
        System.out.println(stringLine);

        // Чтение всего файла появилось в Java 7
        Path path = FileSystems.getDefault().getPath("testData", "test.txt");
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            System.out.println("Чтение всех строк за раз");
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Чтение файла в Stream
        try (BufferedReader reader = new BufferedReader(new FileReader("testData/test.txt"));
             Stream<String> stream = reader.lines();
        ) {
            String lines = stream.collect(Collectors.joining("\n"));
            System.out.println("Чтение файла в поток");
            System.out.println(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Чтение из файла в Stream как Java 8
        try (Stream<String> stream = Files.newBufferedReader(path).lines()) {
            System.out.println("Чтение из файла в поток Java 8 style");
            stream.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Пример записи в файл и переопределение стандартного потока")
    public void exWriteInFile() {
        String[] exString = {"test1", "test2", "test3", "test4"};
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("testData/newfile.txt")))) {
            writer.println("Буфферизованный вывод в файл");
            for (String str : exString) {
                writer.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Передача потока ввода в файл
        PrintStream defaultStream = System.out;
        try (PrintStream stream = new PrintStream(new FileOutputStream("testData/newfile.txt"))) {
            System.out.println("Здесь я пишу в консоль но в след строках будет запись в файл");
            stream.println("Это первая строка записанная в файл");
            for (String str : exString) {
                stream.println(str);
            }
            System.setOut(stream);
            System.out.println("Это тоже строка отправленная в файл");
            System.out.println("После установки потока вместо консоли в файл получили это");
            System.setOut(defaultStream);
            System.out.println("Это уже должно уйти в консоль");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
