package co.edu.uniquindio.gestorContactos.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ContactosApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(ContactosApp.class.getResource("/ventanaPrincipal.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent, 1000, 360);
        stage.setScene(scene);
        stage.setTitle("Aplicación contactos");
        //stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch(ContactosApp.class, args);
    }

}

