package org.germain.gui;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.controlsfx.control.ListSelectionView;

import java.net.URL;
import java.util.ResourceBundle;

public class ControlFXessaiController implements Initializable {
    public ListSelectionView<String> listAselection = new ListSelectionView<>();
    Label label1 = new Label("Candidats");
    Label label2 = new Label("Passage");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listAselection.getSourceItems().addAll("paul", "marius", "jean");
        listAselection.setSourceHeader(label1);
        listAselection.setTargetHeader(label2);
    }
}
