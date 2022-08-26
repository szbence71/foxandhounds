package hu.unideb.inf.foxandhounds.game;

/**
 * Egy 2 dimenziós vektor adatait tároló rekordosztály.
 * Automatikusan tartalmaz egy {@link #x} és egy {@link #y}, és ahhoz automatikusan egy gettert.
 * Automatikus beállításra kerül a {@code toString()} és {@code hashCode()} metódus, mely
 * lehetővé teszi a vektorok közötti összehasonlítást.
 * @param x x koordináta
 * @param y y koordináta
 */
public record Vector2(int x, int y) { }