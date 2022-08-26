package hu.unideb.inf.foxandhounds.application;

import hu.unideb.inf.foxandhounds.ExcludeFromGeneratedReport;

/**
 * Menteni kívánt adatot reprezentáló osztály.
 */
@ExcludeFromGeneratedReport
public class GameData {
    /**
     * Róka nyeréseinek száma.
     */
    public int foxWins;

    /**
     * Kutya nyeréseinek száma.
     */
    public int houndsWins;

    /**
     * Megadja a Róka és a Kutya nyeréseinek számát, adott értékekre.
     * @param foxWins A Róka nyeréseinek szám
     * @param houndsWins A Kutya nyeréseinek száma
     */
    public GameData(int foxWins, int houndsWins) {
        this.foxWins = foxWins;
        this.houndsWins = houndsWins;
    }

    /**
     * Megadja a Róka és Kutya nyeréseinek számát, megadott adat nélkül (alapértelmezett 0).
     */
    public GameData() {
        this(0, 0);
    }

    /**
     * Az osztály értékeit String formában adja vissza.
     * @return osztály értékeit olvasható formában.
     */
    @Override
    public String toString() {
        return "GameData[foxWins=" + foxWins + ", houndsWins=" + houndsWins + "]";
    }
}
