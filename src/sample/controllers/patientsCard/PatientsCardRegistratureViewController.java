package sample.controllers.patientsCard;

public class PatientsCardRegistratureViewController extends PatientsCardController {
    @Override
    public void onExit() {
        mainApp.openPage("RegistratureMain");
    }
}
