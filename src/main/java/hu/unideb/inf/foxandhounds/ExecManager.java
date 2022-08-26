package hu.unideb.inf.foxandhounds;

import hu.unideb.inf.foxandhounds.application.ApplicationExec;

/**
 * Az alkalmazás indítását végző osztály a {@link #main(String[])} megadott argumentjumai alapján.
 * Konzolos esetén {@link FoxAndHounds} fut le, egyéb esetben {@link ApplicationExec} fut le.
 */
@ExcludeFromGeneratedReport
public class ExecManager {
    public static void main(String[] args) {
        if(args.length > 0 && args[0].equalsIgnoreCase("console")) {
            FoxAndHounds.main(args);
        } else {
            ApplicationExec.main(args);
        }
    }
}
