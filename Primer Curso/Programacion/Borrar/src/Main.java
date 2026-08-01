//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    Notificacio n = new NotificacioEmail("Hola");
    n.enviar();
}
class Notificacio {
    protected String missatge;

    public Notificacio(String missatge) {
        this.missatge = missatge;
    }

    public void enviar() {
        System.out.println("Enviant: " + missatge);
    }
}

class NotificacioEmail extends Notificacio {

    public NotificacioEmail(String missatge) {
        super(missatge);
    }

    public void enviar() {
        System.out.println("EMAIL -> " + missatge);
    }
}
