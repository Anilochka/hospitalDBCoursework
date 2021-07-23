package sample.controllers.specializations;

public class SpecializationsListPatientViewController extends SpecializationsListController {
    @Override
    public void onExit() {
        mainApp.openPage("PatientMain");
    }
}
