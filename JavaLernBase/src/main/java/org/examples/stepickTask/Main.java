package org.examples.stepickTask;

import java.io.IOException;

public class Main {

    /**
     * Преобразование строк из формата Windows в формат Unix
     * Пример
     * Из System.in зачитаны следующие байты: 65 13 10 10 13.
     * Внимание! Это не строка "65 13 10 10 13", а последовательность чисел, возвращаемая методом System.in.read().
     * В System.out должны быть выведены байты: 65 10 10 13
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{
            int first = System.in.read();
            int second;
            while (first != -1) {
                second = System.in.read();
                if (first != 13 || second != 10) {
                    System.out.write(first);
                }
                first = second;
            }
            System.out.flush();
    }
}
