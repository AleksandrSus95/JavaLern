package org.examples.genericClass.WildcardExample;

public class HomeAnimal extends Animals{
    private String animalName;
    private int id;
    public HomeAnimal(String animalName, int id){
        this.animalName = animalName;
        this.id = id;
    }

    public String getHomeAnimalName() {
        return this.animalName;
    }

    public int getId(){
        return this.id;
    }

    @Override
    public String toString(){
        return HomeAnimal.class.getSimpleName() + " { AnimalName:" + this.animalName + " AnimalId:" + this.id + "}";
    }
}
