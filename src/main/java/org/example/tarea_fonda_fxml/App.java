package org.example.tarea_fonda_fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {

    private static final VISTA_PRINCIPAL = "org.example.tarea_fonda_fxml"
    @Override
    public void start(Stage stage) throws Exception {
        URL vista = App.class.getResource(VISTA_PRINCIPAL);
        if (vista == null) {
            throw new IllegalStateException("No se encontró la vista principal: " + VISTA_PRINCIPAL);
        }


        Parent root = FXMLLoader.load(vista);
        stage.setTitle("Vivero Hojas del Sur");
        stage.setScene(new Scene(root));
        stage.show();




    }
}
