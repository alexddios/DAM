package Ej14Herencias;

public class Main {
    static void main() {
        Animal animal= new Animal();
        Gato gato = new Gato();
        Perro perro= new Perro();
        animal.hacerSonido();
        gato.hacerSonido();
        perro.hacerSonido();
    }
}
