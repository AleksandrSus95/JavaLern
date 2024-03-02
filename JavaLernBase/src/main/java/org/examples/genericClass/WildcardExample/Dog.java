package org.examples.genericClass.WildcardExample;

public class Dog extends HomeAnimal {
    public Dog(String animalName, int id) {
        super(Dog.class.getSimpleName() + ": " + animalName, id);
    }

    public void game(){
        System.out.println(super.getHomeAnimalName() + " playing with you");
    }
}
