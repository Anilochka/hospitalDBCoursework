package sample.controllers.records;

public class RecordsRegistratureViewController extends RecordsController {
    @Override
    public void onExit() {
        mainApp.openPage("menu/RegistratureMain");
    }
}
