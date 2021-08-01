package sample.controllers.patientsCard;

public class PatientsCardMainDoctorViewController extends PatientsCardController {
    @Override
    public void onExit()  {
        mainApp.openPage("menu/MainDoctorMain");
    }
}
