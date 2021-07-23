package sample.controllers.records;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import sample.service.StaffService;

public class RecordAddRegistratureViewController extends RecordAddController {
    @FXML
    public ComboBox<String> doctorsChoice;

    public int getDocId() {
        staffService = new StaffService(connection);
        doctorsChoice.setOnMouseClicked(event -> {
            ObservableList<String> staffFIO = FXCollections.observableArrayList(staffService.getStaffsFIO());

            ObservableList<String> newDoc = staffFIO;
            ObservableList<String> oldDoc = doctorsChoice.getItems();
            if (!newDoc.equals(oldDoc)) {
                doctorsChoice.setItems(newDoc);
            }
        });

        doctorsChoice.setOnAction(event -> {
            String doctor = doctorsChoice.getValue();
            if (doctor != null) {
                String[] subStr;
                String delimeter = " ";
                subStr = doctor.split(delimeter);
                docId = staffService.getStaffByFIO(subStr[0], subStr[1], subStr[2]).getIdStaff();
            }
        });
        return docId;
    }
}
