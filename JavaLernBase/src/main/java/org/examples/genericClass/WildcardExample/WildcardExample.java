package org.examples.genericClass.WildcardExample;

import java.util.ArrayList;
import java.util.List;

public class WildcardExample {
    public static void main(String[] args) {
        List<HomeAnimal> listHomeAnimals = new ArrayList<>();
        listHomeAnimals.add(new HomeAnimal("Sharic", 1));
        listHomeAnimals.add(new HomeAnimal("Murca", 2));

        List<Dog> listDog = new ArrayList<>();
        listDog.add(new Dog("Richard", 3));
        listDog.add(new Dog("Bobik", 4));

        List<Animals> listAnimals = new ArrayList<>();
        listAnimals.add(new Animals());
        listAnimals.add(new Animals());

        test1(listHomeAnimals);
//        test1(listDog); compile error Animal not Dog этот метод может работать только со списком объектов Animal
        test2(listDog);
//        test3(listAnimals); compile error только наследника HomeAnimal включая HomeAnimal
        test3(listDog);
//        test4(listDog); compile error только родители HoneAnimal включая HomeAnimal
        test4(listAnimals);
    }

    public static void test1(List<HomeAnimal> list){
        try {
            System.out.println(WildcardExample.class.getMethod("test1", List.class) + " Этот метод принимает только объекты Animal");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        list.forEach(f -> System.out.println(f.toString()));
        list.forEach(f -> System.out.println(f.getHomeAnimalName()));
        list.forEach(Animals::eat);
    }

    public static void test2(List<?> list){
        try {
            System.out.println(WildcardExample.class.getMethod("test2", List.class) + " Этот метод принимает любые объекты но здесь можем вызывать только методы класса Object");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        list.forEach(f -> System.out.println(f.getClass().getName()));//Только методы Object
    }

    public static void test3(List<? extends HomeAnimal> list){
        try {
            System.out.println(WildcardExample.class.getMethod("test3", List.class) + " Этот метод принимает объекты HomeAnimal и их наследников");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        list.forEach(f -> System.out.println(f.getHomeAnimalName()));
        list.forEach(Animals::run);
    }

    public static void test4(List<? super HomeAnimal> list){
        try {
            System.out.println(WildcardExample.class.getMethod("test4", List.class) + " Этот метод принимает объекты HomeAnimal и их родителей");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        list.forEach(f -> System.out.println(f.getClass().getName()));//Только методы Object
    }
}
