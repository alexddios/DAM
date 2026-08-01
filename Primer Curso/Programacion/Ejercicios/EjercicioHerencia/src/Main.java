//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    Tasca tasca = new Tasca("Matematicas","Deberes de matematicas");
    TascaPrioritat tascaPrioritat = new TascaPrioritat("Alta","programación","Deberes de programación");
    TascaDataLimit tascaDataLimit = new TascaDataLimit("30/01/206","lenguaje de Marcas","Deberes de lenguaje de Marcas");
    List<Tasca> tasques = new ArrayList<>();
    tasques.add(tasca);
    tasques.add(tascaPrioritat);
    tasques.add(tascaDataLimit);
    for(Tasca t : tasques){
        System.out.println("-------");
        t.informacio();
        t.particular();

    }
}
