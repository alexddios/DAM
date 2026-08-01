package Apartado2;

import java.util.ArrayList;
import java.util.List;

public class Refugi {
    private String localitat;
    private List<Animal> animals;

    public Refugi(String localitat){
        this.localitat = localitat;
        this.animals = new ArrayList<>();
    }
    public boolean afegirAnimal(Animal animal){
        return this.animals.add(animal);
    }
    public boolean animalNecesitat(){
        for (Animal animal : animals){
            if (animal.getEdat() < 2 && animal.getNecesitaMedicacio()){
                System.out.printf("L'animal %s que té %d any necesita medicació ",animal.getNom(),animal.getEdat());
                return true;
            }
        }
        return false;
    }

}
