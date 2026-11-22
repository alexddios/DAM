package config;

public record Config(int chispas, int puntosFrios, int enfriamiento) {
    public static final int CHISPAS_MIN = 0;
    public static final int CHISPAS_MAX = 100;
    public static final int PUNTO_FRIO_MIN = 0;
    public static final int PUNTO_FRIO_MAX = 100;
    public static final int ENFRIAMIENTO_MIN = 0;
    public static final int ENFRIAMIENTO_MAX = 10;
}
