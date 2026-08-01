package Apartado2;

public class Main {
    public static void main(String[] args) {
        Refugi refugi = new Refugi("Madrid");
        Animal animal = new Animal("Peix", 2, true);
        Animal animal2 = new Animal("Tauro", 1, false);
        Animal animal3 = new Animal("Medusa", 1, true);
        refugi.afegirAnimal(animal);
        refugi.afegirAnimal(animal2);
        refugi.afegirAnimal(animal3);
        refugi.animalNecesitat();
    }
}
