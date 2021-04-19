package org.germain.model;

import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.germain.Main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FeuilleExcel {
    Workbook wb;
    Sheet sheet;

    /**
     * Méthode d'écriture du fichier résultant
     * @return true si l'opération réussi
     * @throws IOException
     */
    public boolean writeFile() throws IOException {
        boolean success = true;
        DirectoryChooser directoryChooser = new DirectoryChooser();
        String dossier = directoryChooser.showDialog(Main.getPrimaryStage()).toString();

        File fichier = new File(dossier+"/output.xlsx");
        FileOutputStream outputStream = new FileOutputStream(fichier);
        String adresseEnrg =fichier.getAbsolutePath();

        try {
            wb.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }

        outputStream.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Affiche enregistrée ici: \n"+adresseEnrg);
        alert.showAndWait();

        return success;
    }
}
