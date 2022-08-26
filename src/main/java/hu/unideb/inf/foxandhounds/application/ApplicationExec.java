package hu.unideb.inf.foxandhounds.application;

import hu.unideb.inf.foxandhounds.ExcludeFromGeneratedReport;
import javafx.application.Application;

/**
 * A grafikus felület indításáért felelős osztály.
 */
@ExcludeFromGeneratedReport
public class ApplicationExec {
    public static void main(String[] args) {
        Application.launch(GameApplication.class, args);
    }
}
