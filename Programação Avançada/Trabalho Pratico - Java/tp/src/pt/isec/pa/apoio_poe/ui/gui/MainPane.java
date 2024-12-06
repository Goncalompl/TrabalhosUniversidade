package pt.isec.pa.apoio_poe.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.fsm.MaquinaEstados;
import pt.isec.pa.apoio_poe.model.fsm.MaquinaEstadosObservable;

public class MainPane extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        RootPane root = new RootPane(new MaquinaEstadosObservable(new MaquinaEstados()));
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestão de Projetos/Estágios");


        primaryStage.show();

    }
}
