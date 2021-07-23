package sample.controllers.patientsCard;

public class PatientCardDoctorViewController extends PatientsCardController {
    @Override
    public void onExit()  {
        mainApp.openPage("DoctorMain");
    }
}
