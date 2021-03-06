package org.germain.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.germain.Main;
import org.germain.model.Candidat;
import org.germain.tools.HeureExam;
import org.germain.tools.ModelLoader;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static javafx.scene.input.KeyCode.ENTER;

public class MenuController implements Initializable {
    public JFXButton generate;
    public TextField cand1;
    public TextField cand2;
    public TextField cand3;
    public TextField cand4;
    public MenuItem testMod;
    public TextField nomJuryField1;
    public TextField nomJuryField2;

    public HBox horaire;
    public JFXComboBox<String> heure;
    public JFXComboBox<String> minute;
    public TableView<Candidat> candidatTable;
    public TableColumn suppCol;
    public TableColumn<Candidat, String> nomCol;
    public TableColumn<Candidat, String> prenomCol;
    public TextField nomField;
    public TextField prenomField;
    public MenuItem menuGenerate;
    public MenuItem handleQuit;
    public MenuBar menuBar;
    public DatePicker dateExam;
    public MenuItem testMod1;
    public MenuItem about;
    ObservableList<String> heureValue = FXCollections.observableArrayList();
    ObservableList<String> minuteValue = FXCollections.observableArrayList();

    public ObservableList<Candidat> list = FXCollections.observableArrayList();
    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");




    /**
     * G??n??ration affiche selon le mod??le pr??d??fini
     *
     * @param actionEvent
     * @throws IOException
     */
    public void handleGenerate(ActionEvent actionEvent) throws IOException {
        boolean ok = true;
        List<String> list = new ArrayList<>();
        String stamp;
        String adresseEnrg = null;
        String msg = "";
        //R??cup des candidats
        for (Candidat candidat : this.list) {
            list.add(candidat.getLastName() + " " + candidat.getFirstName());
        }

        //Ouverture du Mod??le
        ModelLoader loader = new ModelLoader("cda");
        InputStream is = loader.getModelStream();
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);

        String dateInFrench = null;
        //Gestion de la date d'examen
        if (!(dateExam.getValue() ==null)){
            java.sql.Date datePick = java.sql.Date.valueOf(dateExam.getValue());
            stamp = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.FRANCE).format(datePick);
            String stamp2 = SimpleDateFormat.getDateInstance(
                    SimpleDateFormat.LONG, Locale.FRANCE).format(new Date());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(datePick);
            LocalDate localDate=LocalDate.of(2016,01,01);

            //Day of week and month in French
            dateInFrench=localDate.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy",Locale.FRENCH));
            System.out.println("'2016-01-01' in French: "+dateInFrench);
            Cell date = sheet.getRow(6).getCell(3);
            date.setCellValue(stamp);

        } else {
            ok = false;
            msg += "Veuillez renseigner une date d'examen \n";
            //LocalDate localDate=LocalDate.of(2016,01,01);
            //dateInFrench=localDate.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy",Locale.FRENCH));
        }

        //Ecriture des candidats
        for (int i = 0; i < list.size(); i++) {
            Cell candidat = sheet.getRow(i + 10).getCell(5);
            candidat.setCellValue(list.get(i));
        }

        is.close();

        //Ecriture des Jurys
        Cell jury1 = sheet.getRow(8).getCell(3);
        Cell jury2 = sheet.getRow(8).getCell(5);

        jury1.setCellValue(nomJuryField1.getText());
        jury2.setCellValue(nomJuryField2.getText());


        //Sauvegarde du fichier
        if (ok){
            DirectoryChooser directoryChooser = new DirectoryChooser();
            String dossier = directoryChooser.showDialog(Main.getPrimaryStage()).toString();

            File fichier = new File(dossier+"/output.xlsx");
            FileOutputStream outputStream = new FileOutputStream(fichier);
            adresseEnrg =fichier.getAbsolutePath();
            workbook.write(outputStream);

            outputStream.close();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Affiche enregistr??e ?? \n"+adresseEnrg);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setContentText(msg);
            alert.showAndWait();
        }
        workbook.close();



    }

    /**
     * chargement de l'heure de d??but en minutes
     *
     * @param actionEvent
     */
    public int loadCells(ActionEvent actionEvent) {
        String time;
        time = heure.getSelectionModel().getSelectedItem();
        int timeNum = Integer.parseInt(heure.getSelectionModel().getSelectedItem()) * 60;
        timeNum += Integer.parseInt(minute.getSelectionModel().getSelectedItem());
        time += ":";
        time += minute.getSelectionModel().getSelectedItem();
        System.out.println(time);
        System.out.println("En minutes = " + timeNum);

        System.out.println(new HeureExam(Integer.parseInt(heure.getSelectionModel().getSelectedItem()),Integer.parseInt(minute.getSelectionModel().getSelectedItem())).heureExamList(4));



        return timeNum;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //On charge le tableau
        prenomField.setOnKeyPressed(e -> {
            if(e.getCode() == ENTER){
                System.out.println("entr??");
                Candidat candidat = new Candidat(prenomField.getText(), nomField.getText());
                prenomField.clear();
                nomField.clear();
                list.add(candidat);
            }
        });
        initTableau();

        initTimePicker();

    }

    /**
     * Chargement du tableau draggable
     */
    private void initTableau() {
        nomCol.setCellValueFactory(new PropertyValueFactory<Candidat, String>("lastName"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<Candidat, String>("firstName"));




        // ajout de boutons de suppression
        suppCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        Callback<TableColumn<Candidat, String>, TableCell<Candidat, String>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Candidat, String> call(final TableColumn<Candidat, String> param) {
                        final TableCell<Candidat, String> cell = new TableCell<>() {
                            final FontIcon rem = new FontIcon();
                            final JFXButton btn = new JFXButton("", rem);


                            {
                                rem.setIconLiteral("fas-trash");

                                DropShadow dropShadow = new DropShadow();
                                dropShadow.setOffsetX(1.0);
                                dropShadow.setOffsetY(1.0);


                                btn.setEffect(dropShadow);

                                btn.setOnAction(event -> candidatTable.getItems().remove(getIndex()));
                            }

                            // des boutons sur les lignes contenant du texte
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(btn);
                                }
                                setText(null);
                            }
                        };
                        return cell;
                    }
                };

        suppCol.setCellFactory(cellFactory);

        candidatTable.setItems(list);

        // ajout de la possibilit?? de changer l'ordre par dragg
        candidatTable.setRowFactory(tv -> {
            TableRow<Candidat> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != (Integer) db.getContent(SERIALIZED_MIME_TYPE)) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    Candidat draggedCandidat = candidatTable.getItems().remove(draggedIndex);

                    int dropIndex;

                    if (row.isEmpty()) {
                        dropIndex = candidatTable.getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }

                    candidatTable.getItems().add(dropIndex, draggedCandidat);

                    event.setDropCompleted(true);
                    candidatTable.getSelectionModel().select(dropIndex);
                    event.consume();
                }

            });

            return row;
        });

    }

    /**
     * Chargement de l'??diteur d'heure de d??but
     */
    private void initTimePicker() {
        DecimalFormat dc = new DecimalFormat("#00");
        for (int i = 0; i < 59; i++) {
            minuteValue.add(dc.format(i));
        }
        for (int i = 8; i < 17; i++) {
            heureValue.add(dc.format(i));
        }

        minute.setItems(minuteValue);
        minute.getSelectionModel().select(30);
        heure.setItems(heureValue);
        heure.getSelectionModel().select(0);


    }


    /**
     * Ajout de candidat
     * @param actionEvent
     */
    public void handleAjout(ActionEvent actionEvent) {
        Candidat candidat = new Candidat(prenomField.getText(), nomField.getText());
        prenomField.clear();
        nomField.clear();
        list.add(candidat);
    }


    /**
     * Bouton quitter
     * @param actionEvent
     */
    public void quit(ActionEvent actionEvent) {
        System.exit(0);
    }


    public void loadFakeCandidats(ActionEvent actionEvent) {
        list.add(new Candidat("Germain", "SIPIERE"));
        list.add(new Candidat("Isabella", "Johnson"));
        list.add(new Candidat("Ethan", "Williams"));
        list.add(new Candidat("Michael", "Brown"));

        nomJuryField1.setText("bibi");
        nomJuryField2.setText("Polux");
        LocalDate loc = LocalDate.now();
        dateExam.setValue(loc);
    }

    public void aboutLaunch(ActionEvent actionEvent) {
        Alert.AlertType alertAlertType = AlertType.INFORMATION;
        Alert alert = new Alert(alertAlertType);
        alert.setTitle("Avanc?? du dev");
        alert.setContentText("- Ajout stagiaire ok" +"\n"+
                             "- Ordering par drag ok" + "\n"+
                "- Ajout date exam ok"+ "\n"+
                "- Ajout Jury ko"+"\n"+
                "- Ajout heures ko"+"\n"+
                "- Choix CDA ou DWWM ko"+ "\n"+
                "- R??cap ko"+"\n"+
                "- Sauvegarde pour reprise ult??rieur ko"
        );
        alert.showAndWait();
    }


}
