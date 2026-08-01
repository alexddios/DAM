//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    Persona pep = new Persona("Pepito");
    pep.saludar();
    Persona juan = new Profesor("Juan", "Profesor de Matematicas");
    juan.presentar();
    Alumno alex = new Alumno("Alex","DAM");
    alex.aprender();
    pep.presentar();
    juan.presentar();
    alex.presentar();
}
