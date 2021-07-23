package sample.controllers.records;

public class RecordAddDoctorViewController extends RecordAddController {
    @Override
    public int getDocId() {
        docId = staffService.getStaffByUserId(user.getIdUser()).getIdStaff();
        return docId;
    }
}
