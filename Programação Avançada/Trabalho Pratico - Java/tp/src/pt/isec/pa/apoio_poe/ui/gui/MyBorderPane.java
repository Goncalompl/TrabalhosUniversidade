package pt.isec.pa.apoio_poe.ui.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MyBorderPane extends BorderPane {

    MyBorderPane(){
        Label lblISEC = new Label(" Programação Avançada - ISEC 21/22");
        lblISEC.setScaleX(1.5);
        lblISEC.setScaleY(1.5);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(lblISEC);
        AnchorPane.setRightAnchor(lblISEC, 55.0);
        AnchorPane.setBottomAnchor(lblISEC, 10.0);
        this.setBottom(anchorPane);
    }

}
