package ministryofeducation.sideprojectspring.factory;

import ministryofeducation.sideprojectspring.personnel_list.domain.Personnel;
import ministryofeducation.sideprojectspring.personnel_list.domain.department.Department;

import java.time.LocalDate;

public class PersonnelFactory {

    public static Personnel testPersonnel(String name){
        return new Personnel(
                name,
                LocalDate.now(),
                "010-0000-0000",
                "032-000-0000",
                "testEmail@gmail.com",
                "인천광역시 서구 석남동",
                "",
                Department.JOSHUA
        );
    }
}
