package botiga;

import botiga.model.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- INICIANDO BATERÍA DE PRUEBAS ---");

        // 1. CREACIÓN DE ENTIDADES BASE (Herencia y Constructores)
        // El Producte hereda de ArticleCatalog, por lo que su constructor llama a super()
        System.out.println("\n1. Creando catálogo y clientes...");
        Producte prod1 = new Producte("P001", "Portàtil Gaming", 1000.0);
        Producte prod2 = new Producte("P002", "Ratolí sense fils", 20.0);

        Client client1 = new Client("43215678X", "Joan Pons");

        // 2. CREACIÓN DEL PEDIDO (Inicialización coherente)
        // El constructor inicializa el estado a PENDENT y crea la lista vacía de líneas[cite: 58, 59, 62].
        Comanda comanda = new Comanda(101, client1);
        System.out.println("Comanda " + 101 + " creada para el cliente " + "Joan Pons" + ".");

        // 3. COMPOSICIÓN Y REGLAS DE NEGOCIO (Añadir líneas)
        // La Comanda se encarga de hacer el 'new' de LiniaComanda (composición)[cite: 506].
        System.out.println("\n2. Añadiendo líneas al pedido...");

        // Caso de éxito: Cantidad válida (>0)
        boolean linea1Ok = comanda.afegirLinia(prod1, 1, prod1.getPreu());
        System.out.println("Añadir Portátil (Cant: 1): " + (linea1Ok ? "ÉXITO" : "FALLO"));

        boolean linea2Ok = comanda.afegirLinia(prod2, 2, prod2.getPreu());
        System.out.println("Añadir Ratón (Cant: 2): " + (linea2Ok ? "ÉXITO" : "FALLO"));

        // Caso de fallo: Cantidad inválida (<=0) según restricción UML [cite: 266, 505]
        boolean linea3Fail = comanda.afegirLinia(prod2, 0, prod2.getPreu());
        System.out.println("Intentar añadir Ratón con cantidad 0: " + (linea3Fail ? "ÉXITO" : "RECHAZADO correctamente"));

        // 4. AGREGACIÓN Y CÁLCULO TOTAL (Uso de dependencias estáticas)
        System.out.println("\n3. Cálculos e Impuestos...");
        // calcularTotal usa la CalculadoraImpostos internamente[cite: 161, 162, 646].
        System.out.println("Total sin descuento (con 21% IVA): " + comanda.calcularTotal() + "€");

        System.out.println("\n4. Aplicando cupón de descuento (Agregación)...");
        CupoDescompte cupo = new CupoDescompte("BLACKFRIDAY", 15.0);
        // La Comanda solo guarda la referencia al cupón preexistente (agregación)[cite: 533].
        comanda.aplicarCupo(cupo);
        System.out.println("Total con 15% de descuento: " + comanda.calcularTotal() + "€");

        // 5. DESACOPLAMIENTO CON INTERFÍCIES
        System.out.println("\n5. Exportación (Desacoplamiento)...");
        // Tratamos el pedido puramente como un ente Exportable[cite: 594, 604].
        Exportable document = comanda;
        System.out.println(document.exportar());

        // 6. RESTRICCIONES DE ESTADO (Máquina de estados simple)
        System.out.println("\n6. Restricciones de Estado UML...");
        // Para este paso, asumimos que hemos añadido un método cancelar() en Comanda[cite: 310, 316].
        // comanda.cancelar(); // Simulación de cambio de estado
        System.out.println("Simulando cancelación del pedido...");

        /* * Si el estado es CANCELADA, no se pueden añadir líneas[cite: 131, 132, 441].
         * Descomentar cuando el método cancelar() esté implementado:
         * * boolean lineaPostCancelacion = comanda.afegirLinia(prod1, 1, prod1.getPreu());
         * System.out.println("Añadir producto tras cancelar: " + (lineaPostCancelacion ? "ÉXITO (Error)" : "RECHAZADO correctamente"));
         */

        System.out.println("\n--- FIN DE LAS PRUEBAS ---");
    }
}