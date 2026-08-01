//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    String marca = "Toyota";
    String modelo = "Corola";
    int velocidad = 0;

    Coche c1 = new Coche(marca,modelo);
    c1.acelerar(20);
    System.out.println(c1.getMarca()+" "+c1.getModelo()+" "+c1.getVelocidad());
    c1.frenar(10);
    c1.setMarca("Tesla");
    c1.setModelo("S Plaid");
    System.out.println(c1.getMarca()+" "+c1.getModelo()+" "+c1.getVelocidad());
    c1.acelerar(100);
    System.out.println(c1.getMarca()+" "+c1.getModelo()+" "+c1.getVelocidad());
    c1.frenar(50);
    System.out.println(c1.getMarca()+" "+c1.getModelo()+" "+c1.getVelocidad());
}
