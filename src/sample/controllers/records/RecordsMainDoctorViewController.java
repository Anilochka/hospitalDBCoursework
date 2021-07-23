package sample.controllers.records;

public class RecordsMainDoctorViewController extends RecordsController {
    @Override
    public void onExit() {
        mainApp.openPage("MainDoctorMain");
    }
}
