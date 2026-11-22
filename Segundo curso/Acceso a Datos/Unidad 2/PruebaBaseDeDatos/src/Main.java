void main() {
    SerieDAO dao = new SerieDAO();
    dao.crearTaula();

    System.out.println("== INSERT ==");
    System.out.println("Files afectades: " + dao.inserir(new Serie("Breaking Bad", "Drama", 5, "Netflix")));
    System.out.println("Files afectades: " + dao.inserir(new Serie("The Mandalorian", "Ciència-ficció", 3, "Disney+")));
    System.out.println("Files afectades: " + dao.inserir(new Serie("Dark", "Ciència-ficció", 3, "Netflix")));

    System.out.println("\n== SELECT (totes) ==");
    dao.obtenirTotes().forEach(System.out::println);

    System.out.println("\n== SELECT amb filtre (Ciència-ficció) ==");
    dao.obtenirPerGenere("Ciència-ficció").forEach(System.out::println);

    int id = dao.obtenirTotes().getFirst().getId();
    System.out.println("\n== UPDATE (id " + id + " -> 6 temporades) ==");
    System.out.println("Files afectades: " + dao.actualitzarTemporades(id, 6));
    dao.obtenirTotes().forEach(System.out::println);

    System.out.println("\n== DELETE (id " + id + ") ==");
    System.out.println("Files afectades: " + dao.eliminar(id));
    dao.obtenirTotes().forEach(System.out::println);
}
